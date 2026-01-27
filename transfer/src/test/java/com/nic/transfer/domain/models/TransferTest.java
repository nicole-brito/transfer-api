package com.nic.transfer.domain.models;

import com.nic.transfer.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransferTest {

    @Test
    void shouldBePositiveValue() {
        var transfer = new Transfer(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );


        var exception = assertThrows(DomainException.class, () -> {
            transfer.setAmount(new BigDecimal("-10.0"));
        } );

        assertEquals("Value must be positive.", exception.getMessage());
    }
}
