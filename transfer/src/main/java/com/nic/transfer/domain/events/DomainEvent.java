package com.nic.transfer.domain.events;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredOn();
}
