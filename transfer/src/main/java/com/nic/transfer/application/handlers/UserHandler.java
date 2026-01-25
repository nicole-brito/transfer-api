package com.nic.transfer.application.handlers;

import com.nic.transfer.application.commands.CreateUserCommand;
import com.nic.transfer.domain.models.user.User;
import com.nic.transfer.domain.ports.out.EventStorePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserHandler {

    private final EventStorePort eventStore;

    public UserHandler(EventStorePort eventStore) {
        this.eventStore = eventStore;
    }

    @Transactional
    public UUID handle(CreateUserCommand command) {
        UUID userId = UUID.randomUUID();

        User user = new User(
                userId,
                command.fullName(),
                command.cpf(),
                command.email(),
                command.userType(),
                command.balance()
        );

        eventStore.saveEvents(user.getId(), user.getUncommitedEvents(), -1L);

        user.clearEvents();

        return userId;
    }
}
