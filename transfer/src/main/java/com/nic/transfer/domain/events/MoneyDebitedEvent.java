package com.nic.transfer.domain.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record MoneyDebitedEvent(
        UUID userId,
        BigDecimal amount,
        Instant occurredOn
) implements DomainEvent {
}
