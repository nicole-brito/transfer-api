package com.nic.transfer.domain.models;

import com.nic.transfer.domain.base.AggregateRoot;
import com.nic.transfer.domain.events.TransferCreatedEvent;
import com.nic.transfer.domain.exceptions.DomainException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Transfer extends AggregateRoot {
    private UUID id;
    private BigDecimal value;
    private UUID payerId;
    private UUID payeeId;
    private Instant occurredOn;

    private final List<Object> uncommittedEvents = new ArrayList<>();

    private void validateTransaction(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Value must be positive.");
        }
    }

    public Transfer create(UUID id, BigDecimal value, UUID payerId, UUID payeeId, Instant occurredOn) {
        this.validateTransaction(value);

        Transfer transfer = new Transfer();
        transfer.apply(new TransferCreatedEvent(id, value, payerId,payeeId, occurredOn));
        return transfer;
    }

    private void apply(TransferCreatedEvent event) {
        this.id = event.id();
        this.value = event.value();
        this.payerId = event.payerId();
        this.payeeId = event.payeeId();
        this.occurredOn = event.occurredOn();

        this.uncommittedEvents.add(event);
    }
}
