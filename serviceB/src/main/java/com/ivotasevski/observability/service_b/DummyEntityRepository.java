package com.ivotasevski.observability.service_b;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DummyEntityRepository extends JpaRepository<DummyEntity, Long> {
}
