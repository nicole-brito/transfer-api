package com.nic.transfer.domain.models.user;

import com.nic.transfer.domain.exceptions.DomainException;

public record Email(String value) {
    public Email {
        if (value == null || !value.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new DomainException("Invalid email");
        }
    }
}
