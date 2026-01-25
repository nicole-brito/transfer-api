package com.nic.transfer.infra.adapters.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        @NotNull UUID payerId,
        @NotNull UUID payeeId,
        @Positive BigDecimal amount
        ) {
}
