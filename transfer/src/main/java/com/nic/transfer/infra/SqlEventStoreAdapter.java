package com.nic.transfer.infra;

import com.nic.transfer.infra.adapters.out.EventEntity;
import com.nic.transfer.infra.adapters.out.repositories.EventJpaRepository;
import com.nic.transfer.domain.events.DomainEvent;
import com.nic.transfer.domain.exceptions.DomainException;
import com.nic.transfer.domain.ports.out.EventStorePort;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

@Repository
public class SqlEventStoreAdapter implements EventStorePort {

    private final EventJpaRepository repository;
    private final ObjectMapper  objectMapper;

    public SqlEventStoreAdapter(EventJpaRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void saveEvents(UUID aggregateId, List<DomainEvent> events, Long expectedVersion) {
        for (DomainEvent event : events) {
            EventEntity entity = new EventEntity();
            entity.setAggregateId(aggregateId);
            entity.setEventType(event.getClass().getSimpleName());
            entity.setOccurredAt(event.occurredOn());
            entity.setVersion(++expectedVersion);

            try {
                entity.setEventData(objectMapper.writeValueAsString(event));
            } catch (DomainException e) {
                throw new RuntimeException("Erro ao serializar o evento.", e);
            }

            repository.save(entity);
        }
    }

    @Override
    public List<DomainEvent> loadEvents(UUID aggregateId) {
        List<EventEntity> entities = repository.findByAggregateIdOrderByVersionAsc(aggregateId);

        return entities.stream().map(entity -> {
            try {
                Class<?> eventClass = Class.forName("com.nic.transfer.domain.events." + entity.getEventType());

                return (DomainEvent) objectMapper.readValue(entity.getEventData(), eventClass);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao reidratar evento", e);
            }
        }).toList();
    }
}