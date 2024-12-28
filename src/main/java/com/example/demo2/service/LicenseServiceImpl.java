package com.example.demo2.service;

import com.example.demo2.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {
    private LicenseRepository licenseRepository;

    @Override
    public String createRandomLicenseNumber() {
        Random random = new Random();
        StringBuilder licenseNumber = new StringBuilder();
        char randomLetter = (char) ('A' + random.nextInt(26));
        int randomInteger = random.nextInt(10);

        return licenseNumber.append(randomLetter).append(randomInteger).toString();
    }
}
