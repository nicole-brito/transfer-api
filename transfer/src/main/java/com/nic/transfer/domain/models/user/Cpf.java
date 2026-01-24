package com.nic.transfer.domain.models.user;

import com.nic.transfer.domain.exceptions.DomainException;

public record Cpf(String value) {
    public Cpf {
        if (value == null || !value.matches("^(\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}|\\d{11})$")) {
            throw new DomainException("Invalid document");
        };
    }
}
