package com.nic.transfer.domain.ports.out;


import com.nic.transfer.domain.events.DomainEvent;

import java.util.List;
import java.util.UUID;

public interface EventStorePort {
    void saveEvents(UUID aggregateId, List<DomainEvent> events, Long expectedVersion);
    List<DomainEvent> loadEvents(UUID aggregateId);
}
