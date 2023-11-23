package com.ivotasevski.observability.service_a;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
class ServiceAController {

    private final RestTemplate restTemplate;

    @GetMapping("/client/resource")
    ResponseEntity<Void> resource() {
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

        return ResponseEntity.ok().build();
    }

}