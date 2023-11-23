package com.ivotasevski.observability.service_b;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
class ServiceBController {

    private final Random random = new Random();
    private final ResourceService resourceService;
    private final DummyEntityRepository repository;

    @GetMapping("/serviceb/small/{id}")
    String resource(@PathVariable("id") String resourceId) {
        var resource = resourceService.getResource(resourceId);
        repository.save(new DummyEntity(Instant.now()));
        return resource;
    }

    @GetMapping("/serviceb/massive/{id}")
    String massiveResource(@PathVariable("id") String resourceId) {
        var resource = resourceService.getMassiveResource(resourceId);
        repository.save(new DummyEntity(Instant.now()));
        return resource;
    }


    @GetMapping("/serviceb/b1")
    @SneakyThrows
    void b1() {
        Thread.sleep(random.nextLong(1000L));
        repository.save(new DummyEntity(Instant.now())); // simulates latency
    }

    @GetMapping("/serviceb/b2")
    @SneakyThrows
    void b2() {
        Thread.sleep(random.nextLong(1000L)); // simulates latency
        repository.save(new DummyEntity(Instant.now()));
    }

    @GetMapping("/serviceb/b3")
    @SneakyThrows
    void b3() {
        Thread.sleep(random.nextLong(1000L)); // simulates latency
        repository.save(new DummyEntity(Instant.now()));
    }

    @GetMapping("/serviceb/b4")
    @SneakyThrows
    void b4() {
        Thread.sleep(random.nextLong(1000L)); // simulates latency
        repository.save(new DummyEntity(Instant.now()));
    }

    @GetMapping("/serviceb/b5")
    @SneakyThrows
    void b5() {
        Thread.sleep(random.nextLong(1000L)); // simulates latency
        repository.save(new DummyEntity(Instant.now()));
    }


    @GetMapping("/serviceb/b6")
    @SneakyThrows
    void b6() {
        Thread.sleep(random.nextLong(1000L)); // simulates latency
        repository.save(new DummyEntity(Instant.now()));
    }

    @GetMapping("/serviceb/b7")
    @SneakyThrows
    void b7() {
        Thread.sleep(random.nextLong(1000L)); // simulates latency
        repository.save(new DummyEntity(Instant.now()));
    }

    @GetMapping("/serviceb/b8")
    @SneakyThrows
    void b8() {
        Thread.sleep(random.nextLong(1000L)); // simulates latency
        repository.save(new DummyEntity(Instant.now()));
    }

    @GetMapping("/serviceb/b9")
    @SneakyThrows
    void b9() {
        Thread.sleep(random.nextLong(1000L)); // simulates latency
        repository.save(new DummyEntity(Instant.now()));
    }

    @GetMapping("/serviceb/b10")
    @SneakyThrows
    void b10() {
        Thread.sleep(random.nextLong(1000L)); // simulates latency
        repository.save(new DummyEntity(Instant.now()));
    }


}