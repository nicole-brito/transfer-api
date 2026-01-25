package com.nic.transfer.application.commands;

import com.nic.transfer.domain.models.user.UserType;

import java.math.BigDecimal;

public record CreateUserCommand(
        String fullName,
        String cpf,
        String email,
        String password,
        UserType userType,
        BigDecimal balance
) {
}
