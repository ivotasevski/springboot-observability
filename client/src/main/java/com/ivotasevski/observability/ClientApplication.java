package com.ivotasevski.observability;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${app.serviceA.url}")
    private String serviceAUrl;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            try {
                restTemplate.getForEntity(serviceAUrl + "/client/resource", Void.class);
            } catch (Exception e) {
                log.warn("Exception occurred: '{}'", e.getMessage());
            }

            Thread.sleep(2000);
        }
    }
}
