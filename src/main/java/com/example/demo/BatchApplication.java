package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }

    @Autowired
    DbRepo dbRepo;

    @Override
    public void run(String... args) throws Exception {
        dbRepo.validateJdbcTemplate();
    }
}
