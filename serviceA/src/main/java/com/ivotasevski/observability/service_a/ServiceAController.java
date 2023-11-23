package com.ivotasevski.observability.service_a;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.serviceB.url}")
    private String serviceBUrl;

    @GetMapping("/servicea/a1")
    ResponseEntity<Void> a1() {
        getMassiveResource();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/servicea/a2")
    ResponseEntity<Void> a2() {
        getSmallResource();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/servicea/a3")
    ResponseEntity<Void> a3() {
        getSmallResource();
        getMassiveResource();
        return ResponseEntity.ok().build();
    }

    private ResponseEntity getSmallResource() {
        // get small resource
        var smallResourceId = UUID.randomUUID().toString();
        log.info("Will send a request to get small resource. Id={}", smallResourceId);
        ResponseEntity<String> smallResponse;
        smallResponse = restTemplate.exchange(serviceBUrl + "/serviceb/small/{resourceId}", HttpMethod.GET, new HttpEntity<>(null), String.class, smallResourceId);
        log.info("Got response for small resource: {}", smallResponse.getStatusCode());
        return smallResponse;
    }

    private ResponseEntity getMassiveResource() {
        // get massive resource
        var massiveResourceId = UUID.randomUUID().toString();
        log.info("Will send a request to get massive resource. Id={}", massiveResourceId);
        var massiveResponse = restTemplate.exchange(serviceBUrl + "/serviceb/massive/{resourceId}", HttpMethod.GET, new HttpEntity<>(null), String.class, massiveResourceId);
        log.info("Got response for massive resource: {}", massiveResponse.getStatusCode());
        return massiveResponse;
    }

}