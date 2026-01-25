package com.nic.transfer.infra.adapters.out;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    private UUID aggregateId;

    @Setter
    @Getter
    private String eventType;

    @Setter
    @Getter
    @Column(columnDefinition = "TEXT")
    private String eventData;

    @Setter
    private Long version;

    @Setter
    private Instant occurredAt;

}
