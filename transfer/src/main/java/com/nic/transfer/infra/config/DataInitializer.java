package com.nic.transfer.infra.config;

import com.nic.transfer.application.commands.CreateUserCommand;
import com.nic.transfer.application.handlers.UserHandler;
import com.nic.transfer.domain.models.user.UserType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

//@Component
public class DataInitializer implements CommandLineRunner {

    private final UserHandler userHandler;

    public DataInitializer(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @Override
    public void run(String... args) {

        var commonUser = new CreateUserCommand(
                "Usuário Comum",
                "123.456.789-00",
                "common@email.com",
                "123",
                UserType.COMMON,
                new BigDecimal("100.00")
        );

        var merchantUser = new CreateUserCommand(
                "Usuário Lojista",
                "123.456.789-00",
                "merchant@email.com",
                "123",
                UserType.MERCHANT,
                new BigDecimal("0.00")
        );

        UUID idCommon = userHandler.handle(commonUser);
        UUID idMerchant = userHandler.handle(merchantUser);

        System.out.println("ID Common user: " + idCommon);
        System.out.println("ID Merchant: " + idMerchant);
    }
}
