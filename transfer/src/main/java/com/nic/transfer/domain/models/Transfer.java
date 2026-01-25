package com.nic.transfer.domain.models;

import com.nic.transfer.domain.base.AggregateRoot;
import com.nic.transfer.domain.events.DomainEvent;
import com.nic.transfer.domain.events.TransferCreatedEvent;
import com.nic.transfer.domain.exceptions.DomainException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Transfer extends AggregateRoot {
    private UUID id;
    private BigDecimal amount;
    private UUID payerId;
    private UUID payeeId;
    private Instant occurredOn;

    public static Transfer create(UUID id, UUID payerId, UUID payeeId, BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new DomainException("Value must be positive.");
            }

        Transfer transfer = new Transfer();

        transfer.raiseEvent(new TransferCreatedEvent(
                id,
                amount,
                payerId,
                payeeId,
                Instant.now()
        ));

        return transfer;
    }

    @Override
    protected void apply(DomainEvent event) {
        if (event instanceof TransferCreatedEvent e) {
            handle(e);
        }
    }

    private void handle(TransferCreatedEvent event) {
        this.id = event.id();
        this.amount = event.amount();
        this.payerId = event.payerId();
        this.payeeId = event.payeeId();
        this.occurredOn = event.occurredOn();
    }

    public UUID getId() {
        return id;
    }
}
