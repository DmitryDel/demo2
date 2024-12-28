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
/*
У меня есть два микросервиса, но они не взаимодействую между собой. Цель реализовать взаимодействие двух сервисов через
fiegn client используя лучшие практики. Первый микросервис хранит в БД Entity: Person, Car. Второй должен запрашивать
у первого информацию по пути: "/people/{id}", если у человека есть машина, то второй микросервис выдает права и заносит
в свою БД информацию о человеке: Long id (id человека из первой БД), String name, List<Car> и заполняет
поле String license (это поле инициализируется методом, который рандомно генерирует номер в service.License).
Если операция происходит успешно, то выводится ответ пользователю ok("Права успешно получены")
* */