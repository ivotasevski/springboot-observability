package com.ivotasevski.observability.service_b;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Setter
@Getter
public class DummyEntity {

    public DummyEntity() {
    }

    public DummyEntity(Instant time) {
        this.time = time;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Instant time;
}
