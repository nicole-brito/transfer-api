package com.nic.transfer.domain.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransferCreatedEvent(
        UUID id,
        BigDecimal value,
        UUID payerId,
        UUID payeeId,
        Instant occurredOn
) implements DomainEvent {}
