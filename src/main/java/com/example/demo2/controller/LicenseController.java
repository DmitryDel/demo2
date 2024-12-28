package com.example.demo2.controller;

import com.demo.api.dto.PersonResponseDTO;
import com.example.demo2.client.PersonClient;
import com.example.demo2.service.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.ok("License Number: " + licenseNumber);
        }
        return ResponseEntity.notFound().build();
    }
}
