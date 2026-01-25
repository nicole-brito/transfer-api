package com.nic.transfer.domain.base;

import com.nic.transfer.domain.events.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
    private final List<DomainEvent> uncommittedEvents = new ArrayList<>();

    private Long version = -1L;

    protected void raiseEvent(DomainEvent event) {
        this.uncommittedEvents.add(event);

        this.apply(event);
    }

    protected abstract void apply(DomainEvent event);

    public List<DomainEvent> getUncommitedEvents() {
        return uncommittedEvents;
    }

    public void clearEvents() {
        this.uncommittedEvents.clear();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
