package com.nic.transfer.adapters.out;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private UUID aggregateId;

    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String eventData;

    private Long version;

    private Instant occurredAt;

    public void setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventData() {
        return eventData;
    }
}
