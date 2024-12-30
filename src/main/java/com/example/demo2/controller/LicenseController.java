package com.example.demo2.controller;

import com.demo.api.dto.PersonResponseDTO;
import com.example.demo2.client.PersonClient;
import com.example.demo2.service.LicenseService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@RestController
@RequestMapping("/license")
public class LicenseController {
    private final PersonClient personClient;
    private final LicenseService licenseService;

    public LicenseController(PersonClient personClient, LicenseService licenseService) {
        this.personClient = personClient;
        this.licenseService = licenseService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> issueLicense(@PathVariable Long id) {
        ResponseEntity<PersonResponseDTO> response = personClient.getPersonById(id);
        if (response.getStatusCode() == HttpStatus.OK) {
            String licenseNumber = licenseService.createRandomLicenseNumber();

            try {
                // Путь к файлу для сохранения
                String fileName = "license_" + id + ".txt";
                Path path = Paths.get(fileName);

                // Запись номера лицензии в файл
                Files.write(path, Collections.singleton("License Number: " + licenseNumber));

                // Возвращаем URI для скачивания файла
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/license/download/")
                        .path(fileName)
                        .toUriString();

                return ResponseEntity.ok("License Number: " + licenseNumber + ". Download Link: " + fileDownloadUri);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Could not save license number file.");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path path = Paths.get(fileName);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(path);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
