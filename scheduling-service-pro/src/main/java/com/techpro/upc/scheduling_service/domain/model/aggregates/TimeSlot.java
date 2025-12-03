package com.techpro.upc.scheduling_service.domain.model.aggregates;

import com.techpro.upc.scheduling_service.domain.model.commands.CreateTimeSlotCommand;
import com.techpro.upc.scheduling_service.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "time_slots")
public class TimeSlot extends AuditableAbstractAggregateRoot<TimeSlot> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name="end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name="psychologist_id", nullable = false)
    private Long psychologistId;

    @Column(nullable = false)
    private boolean isReserved = false; // Nombre corregido

    protected TimeSlot() {}

    public TimeSlot(CreateTimeSlotCommand command) {
        this.startTime = command.startTime();
        this.endTime   = command.endTime();
        this.psychologistId = command.psychologistId();
        this.isReserved = false;
    }

    // --- MÉTODOS QUE FALTAN ---
    public boolean isReserved() {
        return this.isReserved;
    }

    public void markAsReserved() {
        this.isReserved = true;
    }
}
