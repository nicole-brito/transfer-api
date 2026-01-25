package com.nic.transfer.infra.adapters.in;

import com.nic.transfer.application.commands.CreateTransferCommand;
import com.nic.transfer.application.handlers.TransferHandler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferHandler transferHandler;

    public TransferController(TransferHandler transferHandler) {
        this.transferHandler = transferHandler;
    }

    @PostMapping
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferRequest request) {
        var command = new CreateTransferCommand(
                request.payerId(),
                request.payeeId(),
                request.amount()
        );

        transferHandler.handle(command);

        return ResponseEntity.ok().build();
    }
}
