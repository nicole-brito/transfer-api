package com.nic.transfer.application.handlers;

import com.nic.transfer.application.commands.CreateTransferCommand;
import com.nic.transfer.domain.models.user.User;
import com.nic.transfer.domain.ports.out.EventPublisher;
import com.nic.transfer.domain.ports.out.EventStorePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferHandler {

    private final EventStorePort eventStore;
//    private final EventPublisher eventPublisher;

    public TransferHandler(EventStorePort eventStore) {
        this.eventStore = eventStore;
    }

    @Transactional
    public void handle(CreateTransferCommand command) {
        User payer = User.fromEvents(eventStore.loadEvents(command.payerId()));

    }
}
