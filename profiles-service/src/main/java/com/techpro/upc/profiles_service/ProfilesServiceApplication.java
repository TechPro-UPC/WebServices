package com.techpro.upc.profiles_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.techpro.upc.profiles_service.infrastructure.iam")
public class ProfilesServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProfilesServiceApplication.class, args);
    }
}
