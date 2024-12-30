package com.example.demo2.service;

import com.example.demo2.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {
    private LicenseRepository licenseRepository;

    @Override
    public String createRandomLicenseNumber() {
        Random random = new Random();
        StringBuilder licenseNumber = new StringBuilder();

        String randomLetter = IntStream.range(0, 4)
                .mapToObj(el -> (char)('A' + random.nextInt(26)))
                .map(String::valueOf).collect(Collectors.joining());
        for (int i = 1; i <= 9; i++) {
            licenseNumber.append(random.nextInt(9) + 1);
        }

        return randomLetter + "-" + licenseNumber;
    }
}
