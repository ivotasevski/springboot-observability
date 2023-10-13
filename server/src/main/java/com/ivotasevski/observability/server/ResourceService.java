package com.ivotasevski.observability.server;

import io.micrometer.observation.annotation.Observed;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
class ResourceService {
    private final Random random = new Random();

    // Example of using an annotation to observe methods
    // <small.resource> will be used as a metric name
    // <getting-small-resource> will be used as a span  name
    // <resourceType=SMALL> will be set as a tag for both metric & span
    @Observed(name = "small.resource",
            contextualName = "getting-small-resource",
            lowCardinalityKeyValues = {"resourceType", "SMALL"})
    @SneakyThrows
    public String getResource(String resourceId) {
        log.info("Getting resource with id '{}'", resourceId);
        Thread.sleep(random.nextLong(500L)); // simulates latency

        var num = random.nextInt(10);
        if (num > 6) {
            throw new RuntimeException(String.format("Intentionally generated exception. Source: %s", num));
        }
        return resourceId + "__" + UUID.randomUUID().toString();
    }

    @SneakyThrows
    @Observed
    public String getMassiveResource(String resourceId) {
        log.info("Getting massive resource with id '{}'", resourceId);
        byte[] bytes = new byte[1000*1000* random.nextInt(50)];
        Thread.sleep(2000);
        return resourceId + "__" + UUID.randomUUID().toString();
    }
}