package com.example.demo2.client;

import com.demo.api.dto.PersonResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface PersonClient {

    @GetMapping("/people/{id}")
    ResponseEntity<PersonResponseDTO> getPersonById(@PathVariable Long id);
}
