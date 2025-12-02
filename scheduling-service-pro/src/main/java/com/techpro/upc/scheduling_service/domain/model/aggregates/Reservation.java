package com.techpro.upc.scheduling_service.domain.model.aggregates;


import com.techpro.upc.scheduling_service.domain.model.commands.CreateReservationCommand;
import com.techpro.upc.scheduling_service.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "reservations")
public class Reservation extends AuditableAbstractAggregateRoot<Reservation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private Long psychologistId; // CORREGIDO: 'h' agregada

    @Column(nullable = false)
    private Long paymentId;

    @Column(nullable = false)
    private Long timeSlotId;

    @Column(nullable = false)
    private String status; // Agregamos estado: CONFIRMED, CANCELLED

    protected Reservation() { }

    public Reservation(CreateReservationCommand command) {
        this.patientId      = command.patientId();
        this.psychologistId = command.psychologistId(); // CORREGIDO
        this.paymentId      = command.paymentId();
        this.timeSlotId     = command.timeSlotId();
        this.status         = "CONFIRMED"; // Estado inicial por defecto
    }
}
