package com.course.projectbase;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectBaseApplication {

    @Value("${jwt.secretKey}")
    private String jwtKey;

    public static void main(String[] args) {
        SpringApplication.run(ProjectBaseApplication.class, args);
    }

    @PostConstruct
    public void test() {
        System.out.println("jwtKey: " + jwtKey);
    }
}
