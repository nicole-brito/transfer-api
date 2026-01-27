package com.nic.transfer.application.handlers;

import com.nic.transfer.application.commands.CreateTransferCommand;
import com.nic.transfer.domain.exceptions.DomainException;
import com.nic.transfer.domain.models.Transfer;
import com.nic.transfer.domain.models.user.User;
import com.nic.transfer.domain.ports.out.AuthorizePort;
import com.nic.transfer.domain.ports.out.EventStorePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferHandler {

    private final EventStorePort eventStore;
    private final AuthorizePort authorize;

    public TransferHandler(EventStorePort eventStore, AuthorizePort authorize) {
        this.eventStore = eventStore;
        this.authorize = authorize;
    }

    @Transactional
    public void handle(CreateTransferCommand command) {
        User payer = User.fromEvents(eventStore.loadEvents(command.payerId()));
        User payee = User.fromEvents(eventStore.loadEvents(command.payeeId()));

        payer.debit(command.amount());
        payee.credit(command.amount());

        if (!authorize.isAuthorized(payer, command.amount())) {
            throw new DomainException("Transfer not authorized by the external service.");
        }

        Transfer transfer = Transfer.create(
                UUID.randomUUID(),
                payer.getId(),
                payee.getId(),
                command.amount()
        );

        eventStore.saveEvents(payer.getId(), payer.getUncommitedEvents(), payer.getVersion());

        eventStore.saveEvents(payee.getId(), payee.getUncommitedEvents(), payee.getVersion());

        eventStore.saveEvents(transfer.getId(), transfer.getUncommitedEvents(), -1L);

    }
}
