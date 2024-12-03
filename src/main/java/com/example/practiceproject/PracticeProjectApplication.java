package com.example.practiceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PracticeProjectApplication is the main entry point of the Spring Boot application.
 * It uses the @SpringBootApplication annotation to enable auto-configuration,
 * component scanning, and property support in a Spring application context.
 */
@SpringBootApplication
public class PracticeProjectApplication {

    /**
     * The main method that serves as the entry point for the application.
     * It runs the Spring Boot application by invoking the SpringApplication.run() method.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(PracticeProjectApplication.class, args);
    }

}
