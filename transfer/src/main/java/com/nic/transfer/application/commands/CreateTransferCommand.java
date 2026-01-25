package com.nic.transfer.application.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransferCommand(UUID payerId, UUID payeeId, BigDecimal amount) {
}
