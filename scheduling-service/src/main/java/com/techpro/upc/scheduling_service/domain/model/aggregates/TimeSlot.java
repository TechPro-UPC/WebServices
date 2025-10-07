package com.techpro.upc.scheduling_service.domain.model.aggregates;

import com.techpro.upc.scheduling_service.domain.model.commands.CreateTimeSlotCommand;
import com.techpro.upc.scheduling_service.domain.model.valueobjects.TimeSlotType;
import com.techpro.upc.scheduling_service.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;

import java.time.LocalDateTime;

public class TimeSlot extends AuditableAbstractAggregateRoot<TimeSlot> {
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private boolean status;

    @Embedded
    private TimeSlotType type;

    public TimeSlot() {}


    public TimeSlot(CreateTimeSlotCommand command) {
        this.startTime = command.startTime();
        this.endTime=command.endTime();
        this.status = command.status();
        this.type = new TimeSlotType(command.type());
    }



    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isStatus() {
        return status;
    }

    public String getType() {
        return type.type();
    }

    public void markUnavailable() {
        this.status = false;
    }

    public void markAvailable() {
        this.status = true;
    }
}
