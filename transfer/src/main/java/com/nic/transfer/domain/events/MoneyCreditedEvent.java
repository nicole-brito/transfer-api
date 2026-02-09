package com.nic.transfer.domain.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record MoneyCreditedEvent(
        UUID userId,
        BigDecimal amount,
        Instant occurredOn
) implements DomainEvent {
}
