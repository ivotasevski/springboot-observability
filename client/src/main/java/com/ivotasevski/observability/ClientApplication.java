package com.ivotasevski.observability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
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

    @Value("${app.serviceB.url}")
    private String serviceBUrl;

    private final TaskScheduler scheduler;

    private final Random random = new Random();

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String[] endpoints = new String[]{
                String.format("%s/servicea/a1", serviceAUrl),
                String.format("%s/servicea/a2", serviceAUrl),
                String.format("%s/servicea/a3", serviceAUrl),
                String.format("%s/serviceb/small/%s", serviceBUrl, UUID.randomUUID().toString()),
                String.format("%s/serviceb/massive/%s", serviceBUrl, UUID.randomUUID().toString()),
                String.format("%s/serviceb/b1", serviceBUrl),
                String.format("%s/serviceb/b2", serviceBUrl),
                String.format("%s/serviceb/b3", serviceBUrl),
                String.format("%s/serviceb/b4", serviceBUrl),
                String.format("%s/serviceb/b5", serviceBUrl),
                String.format("%s/serviceb/b6", serviceBUrl),
                String.format("%s/serviceb/b7", serviceBUrl),
                String.format("%s/serviceb/b8", serviceBUrl),
                String.format("%s/serviceb/b9", serviceBUrl),
                String.format("%s/serviceb/b10", serviceBUrl)
        };

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                var endpoint = endpoints[random.nextInt(endpoints.length)];
                try {
                    restTemplate.getForEntity(endpoint, Void.class);
                } catch (Exception e) {
                    log.warn("Exception occurred while invoking endpoint '{}': '{}'", endpoint, e.getMessage());
                }
            }
        }, 300);


    }
}
