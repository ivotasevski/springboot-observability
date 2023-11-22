package com.ivotasevski.observability.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
class ResourceController {

    private final ResourceService resourceService;

    @GetMapping("/resource/small/{id}")
    String resource(@PathVariable("id") String resourceId) {
        log.info("Handling /resource/small/{id}", resourceId);
        var resource =  resourceService.getResource(resourceId);
        log.info("Finished handling /resource/small/{id}", resourceId);
        return resource;
    }

    @GetMapping("/resource/massive/{id}")
    String massiveResource(@PathVariable("id") String resourceId) {
        log.info("Handling /resource/massive/{id}", resourceId);
        var resource =  resourceService.getMassiveResource(resourceId);
        log.info("Finished handling /resource/massive/{id}", resourceId);
        return resource;
    }
}