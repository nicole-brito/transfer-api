package com.nic.transfer.domain.models.user;

import com.nic.transfer.domain.exceptions.DomainException;

import java.math.BigDecimal;
import java.util.UUID;

public class User extends AggregateRoot {
    private UUID id;
    private UserType userType;
    private BigDecimal balance;
    private Email email;
    private Cpf cpf;
    private String name;

    public void validate(BigDecimal amount) {
        if (this.userType == UserType.MERCHANT) {
            throw new DomainException("User type not allowed.");
        }

        if (this.balance.compareTo(amount) < 0) {
            throw new DomainException("Insufficient funds.");
        }
    }
}
