package com.nic.transfer;

import com.nic.transfer.domain.events.MoneyCreditedEvent;
import com.nic.transfer.domain.events.MoneyDebitedEvent;
import com.nic.transfer.domain.events.UserCreatedEvent;
import com.nic.transfer.domain.models.user.User;
import com.nic.transfer.domain.models.user.UserType;
import com.nic.transfer.domain.ports.out.AuthorizePort;
import com.nic.transfer.domain.ports.out.EventStorePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test") //Para usar o application-test.properties
@Transactional //Limpa o banco após cada teste
public class EventStoreIntegrationTest {

    @Autowired
    private EventStorePort eventStore; //Injeção da porta/interface de saída

    @MockitoBean
    private AuthorizePort authorizePort;

    @Test
    void contextLoads() {
        System.out.println("Docker ok");
    }

    @Test
    @DisplayName("Should save events and reconstruct the balance from the db")
    void shouldPersistAndRehydrateUserBalance() {

        //Arrange (passado)
        UUID userId = UUID.randomUUID();
        var event0 = new UserCreatedEvent(userId,
                "User Test",
                "1234",
                "test@email.com",
                UserType.COMMON,
                new BigDecimal("0.00"),
                Instant.now());
        var event1 = new MoneyCreditedEvent(userId, new BigDecimal("100.00"), Instant.now());
        var event2 = new MoneyCreditedEvent(userId, new BigDecimal("50.00"), Instant.now());

        //Act (presente)
        eventStore.saveEvents(userId, List.of(event0, event1, event2), -1L);

        //Assert (futuro)
        var eventsFromDb = eventStore.loadEvents(userId);

        var user = User.fromEvents(eventsFromDb);

        assertEquals(3, eventsFromDb.size(), "Must have 3 events");
        assertEquals(new BigDecimal("150.00"), user.getBalance(), "Balance must be 150.00");
    }

    @Test
    @DisplayName("Should realize a complete transfer")
    void shouldPerformTransferSuccessfully () {
        UUID payerId = UUID.randomUUID();
        UUID payeeId = UUID.randomUUID();

        List eventsPayer = List.of(new UserCreatedEvent(payerId,
                "User Payer",
                "1234",
                "test@email.com",
                UserType.COMMON,
                new BigDecimal("100.00"),
                Instant.now()));

        var eventsPayee = List.of(new UserCreatedEvent(payeeId,
                "User Payee",
                "5678",
                "test@email.com",
                UserType.COMMON,
                new BigDecimal("0.00"),
                Instant.now()));

//        eventStore.saveEvents(payeeId, eventsPayee, -1L);

        Mockito.when(authorizePort.isAuthorized(any(), any())).thenReturn(true);
    }
}
