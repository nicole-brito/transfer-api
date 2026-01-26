package com.nic.transfer.domain.ports.out;

import com.nic.transfer.domain.models.user.User;

import java.math.BigDecimal;

public interface AuthorizePort {
    boolean isAuthorized(User payer, BigDecimal amount);
}
