package com.nic.transfer.domain.events;

import com.nic.transfer.domain.models.user.UserType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record UserCreatedEvent(
        UUID userId,
        String fullName,
        String cpf,
        String email,
        UserType type,
        BigDecimal initialBalance,
        Instant occurredOn
) implements DomainEvent {
}
