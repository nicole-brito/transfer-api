package com.nic.transfer.application.handlers;

import com.nic.transfer.application.commands.CreateTransferCommand;
import com.nic.transfer.domain.ports.out.EventPublisher;
import com.nic.transfer.domain.ports.out.EventStorePort;
import org.springframework.stereotype.Service;

@Service
public class TransferHandler {

    private final EventStorePort eventStore;
    private final EventPublisher eventPublisher;

    public TransferHandler(EventStorePort eventStore, EventPublisher eventPublisher) {
        this.eventStore = eventStore;
        this.eventPublisher = eventPublisher;
    }

    public void handle(CreateTransferCommand command) {

    }
}
