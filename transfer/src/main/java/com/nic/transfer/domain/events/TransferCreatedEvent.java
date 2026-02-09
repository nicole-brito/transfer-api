package com.nic.transfer.domain.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransferCreatedEvent(
        UUID id,
        BigDecimal amount,
        UUID payerId,
        UUID payeeId,
        Instant occurredOn
) implements DomainEvent {}
