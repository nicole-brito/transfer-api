package com.nic.transfer.domain.models;

import com.nic.transfer.domain.exceptions.DomainException;
import com.nic.transfer.domain.models.user.User;
import com.nic.transfer.domain.models.user.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    @Test
    @DisplayName("Should not allow a merchant to make a debit")
    void shouldNotAllowMerchantToDebit() {
        var merchant = new User(
                UUID.randomUUID(),
                "Merchant",
                "123.456.789-10",
                "merchant@email.com",
                UserType.MERCHANT,
                BigDecimal.TEN);

        var exception = assertThrows(DomainException.class, () -> {
            merchant.debit(new BigDecimal("50.0"));
        });

        assertEquals("User type not allowed.", exception.getMessage());
    }

    @Test
    void shouldNotAllowDebitWhenBalanceIsInsufficient() {
        var commonUser = new User(
                UUID.randomUUID(),
                "Common User",
                "123.456.789-00",
                "common@email.com",
                UserType.COMMON,
                BigDecimal.ZERO
        );

        var exception = assertThrows(DomainException.class, () -> {
            commonUser.debit(new BigDecimal("10.0"));
        });

        assertEquals("Insufficient funds.", exception.getMessage());
    }
}
