package com.nic.transfer.domain.models.user;

import com.nic.transfer.domain.base.AggregateRoot;
import com.nic.transfer.domain.events.*;
import com.nic.transfer.domain.exceptions.DomainException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class User extends AggregateRoot {
    private UUID id;
    private UserType userType;
    private BigDecimal balance;
    private Email email;
    private Cpf cpf;
    private String name;

    private User() {
    }

    public User(UUID id, String name, String cpf, String email, UserType type, BigDecimal initialBalance) {
        raiseEvent(new UserCreatedEvent(
                id, name, cpf, email, type, initialBalance, Instant.now()
        ));
    }

    public void validateTransfer(BigDecimal amount) {
        if (this.userType == UserType.MERCHANT) {
            throw new DomainException("User type not allowed.");
        }
    }

    public void debit(BigDecimal amount) {
        this.validateTransfer(amount);

        raiseEvent(new MoneyDebitedEvent(this.id, amount, Instant.now()));
    }

    public void credit(BigDecimal amount) {
        raiseEvent(new MoneyCreditedEvent(this.id, amount, Instant.now()));
    }

    public void apply(DomainEvent event) {
        if (event instanceof UserCreatedEvent e) handle(e);
        else if (event instanceof MoneyDebitedEvent e) handle(e);
        else if (event instanceof MoneyCreditedEvent e) handle(e);
    }

    private void handle(UserCreatedEvent event) {
        this.id = event.userId();
        this.userType = event.type();
        this.balance = event.initialBalance();
    }

    public void handle(MoneyDebitedEvent event) {
        this.balance = this.balance.subtract(event.amount());
    }

    public void handle(MoneyCreditedEvent event) {
        this.balance = this.balance.add(event.amount());
    }

    public static User fromEvents(List<DomainEvent> events) {
        User user = new User();
        events.forEach(user::apply);
        return user;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
