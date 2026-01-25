package com.nic.transfer.application.handlers;

import com.nic.transfer.application.commands.CreateTransferCommand;
import com.nic.transfer.domain.models.Transfer;
import com.nic.transfer.domain.models.user.User;
import com.nic.transfer.domain.ports.out.EventPublisher;
import com.nic.transfer.domain.ports.out.EventStorePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransferHandler {

    private final EventStorePort eventStore;

    public TransferHandler(EventStorePort eventStore) {
        this.eventStore = eventStore;
    }

    @Transactional
    public void handle(CreateTransferCommand command) {
        User payer = User.fromEvents(eventStore.loadEvents(command.payerId()));
        User payee = User.fromEvents(eventStore.loadEvents(command.payeeId()));

        payer.debit(command.amount());

        payee.credit(command.amount());

        Transfer transfer = Transfer.create(
                UUID.randomUUID(),
                payer.getId(),
                payee.getId(),
                command.amount()
        );

        eventStore.saveEvents(payer.getId(), payer.getUncommitedEvents(), payer.getVersion());

        eventStore.saveEvents(payee.getId(), payee.getUncommitedEvents(), payee.getVersion());

        eventStore.saveEvents(transfer.getId(), transfer.getUncommitedEvents(), -1L);

        payer.clearEvents();
        payee.clearEvents();
        transfer.clearEvents();
    }
}
