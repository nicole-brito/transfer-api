package com.nic.transfer.domain.base;

import com.nic.transfer.domain.events.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
    private final List<DomainEvent> uncommitedEvents = new ArrayList<>();

    private Long version = -1L;

    protected void raiseEvent(DomainEvent event) {
        this.uncommitedEvents.add(event);
    }

    public List<DomainEvent> getUncommitedEvents() {
        return uncommitedEvents;
    }

    public void clearEvents() {
        this.uncommitedEvents.clear();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
