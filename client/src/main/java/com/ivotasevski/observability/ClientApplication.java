package com.ivotasevski.observability;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ObservationRegistry registry;
    private final RestTemplate restTemplate;

    private Random random = new Random(); // Simulates potentially large number of values

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            // possible 100000 values
            String highCardinalityValue = "HIGH_" + String.valueOf(random.nextLong(100_000));
            // possible 3 values
            String lowCardinalityValue = "LOW_" + String.valueOf(random.nextLong(3) + 1);
            // Example of using the Observability API manually
            // <resource.observation> is a "technical" name that does not depend on the context. It will be used to name e.g. Metrics
            Observation.createNotStarted("resource.observation", registry)
                    // Low cardinality means that the number of potential values won't be big. Low cardinality entries will end up in e.g. Metrics
                    .lowCardinalityKeyValue("LOW_CARDINALITY", lowCardinalityValue)
                    // High cardinality means that the number of potential values can be large. High cardinality entries will end up in e.g. Spans
                    .highCardinalityKeyValue("HIGH_CARDINALITY", highCardinalityValue)
                    // <command-line-runner> is a "contextual" name that gives more details within the provided context. It will be used to name e.g. Spans
                    .contextualName("command-line-runner")
                    // The following lambda will be executed with an observation scope (e.g. all the MDC entries will be populated with tracing information). Also the observation will be started, stopped and if an error occurred it will be recorded on the observation
                    .observe(() -> {

                        // Since we're in an observation scope - all log lines will contain tracing MDC entries ...

                        try {
                            // get massive resource
                            var massiveResourceId = UUID.randomUUID().toString();
                            log.info("Will send a request to get massive resource. Id={}", massiveResourceId);
                            var massiveResponse = restTemplate.exchange("http://localhost:7654/resource/massive/{resourceId}", HttpMethod.GET, new HttpEntity<>(null), String.class, massiveResourceId);
                            log.info("Got response for massive resource: [{}]", massiveResponse);

                            // get small resource
                            var smallResourceId = UUID.randomUUID().toString();
                            log.info("Will send a request to get small resource. Id={}", smallResourceId);
                            ResponseEntity<String> smallResponse;
                            smallResponse = restTemplate.exchange("http://localhost:7654/resource/small/{resourceId}", HttpMethod.GET, new HttpEntity<>(null), String.class, smallResourceId);
                            log.info("Got response for small resource: [{}]", smallResponse);

                        } catch (Exception e) {
                            log.warn("Exception occurred: '{}'", e.getMessage());
                        }
                    });

            Thread.sleep(2000);
        }

    }
}
