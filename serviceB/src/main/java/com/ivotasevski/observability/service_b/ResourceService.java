package com.ivotasevski.observability.service_b;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
class ResourceService {
    private final Random random = new Random();

    @SneakyThrows
    public String getResource(String resourceId) {
        log.info("Getting resource with id '{}'", resourceId);
        Thread.sleep(random.nextLong(500L)); // simulates latency

        var num = random.nextInt(10);
        if (num > 7) {
            throw new RuntimeException(String.format("Intentionally generated exception. Source: %s", num));
        }
        return resourceId + "__" + UUID.randomUUID().toString();
    }

    @SneakyThrows
    public String getMassiveResource(String resourceId) {
        log.info("Getting massive resource with id '{}'", resourceId);
        byte[] bytes = new byte[1000 * 1000 * random.nextInt(50)];
        Thread.sleep(2000);
        return resourceId + "__" + UUID.randomUUID().toString();
    }
}