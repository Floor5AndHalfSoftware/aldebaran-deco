package com.aldebaran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AldebaranApplication {
    public static void main(String[] args) {
        
        SpringApplication.run(AldebaranApplication.class, args);
    }
}
