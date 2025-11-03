package com.techpro.upc.scheduling_service.domain.model.aggregates;

import com.techpro.upc.scheduling_service.domain.model.commands.CreateTimeSlotCommand;
import com.techpro.upc.scheduling_service.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "time_slots")
public class TimeSlot extends AuditableAbstractAggregateRoot<TimeSlot> {

    @Column(name="start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name="end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name="psychologist_id", nullable = false)
    private Long psychologistId;

    @Column(nullable = false)
    private boolean status = true;

    protected TimeSlot() {}

    public TimeSlot(CreateTimeSlotCommand command) {
        this.startTime = command.startTime();
        this.endTime   = command.endTime();
        this.psychologistId = command.psychologistId();
        this.status = true;
    }

    // Getters (id, createdAt, updatedAt ya vienen de la base con @Getter)
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public Long getPsychologistId() { return psychologistId; }
    public boolean isStatus() { return status; }

    // Comportamiento
    public void markUnavailable() { this.status = false; }
    public void markAvailable()   { this.status = true; }
}
