package com.techpro.upc.scheduling_service.domain.model.aggregates;

import com.techpro.upc.scheduling_service.domain.model.commands.CreateReservationCommand;
import com.techpro.upc.scheduling_service.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation extends AuditableAbstractAggregateRoot<Reservation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)  // paciente (user que reserva)
    private Long patientId;

    @Column(nullable = false)  // psicólogo (provider)
    private Long psycologistId;

    @Column(nullable = false)
    private Long paymentId;

    @Column(nullable = false)
    private Long timeSlotId;

    protected Reservation() { } // requerido por JPA

    public Reservation(CreateReservationCommand command){
        this.patientId     = command.patientId();
        this.psycologistId = command.psycologistId();
        this.paymentId     = command.paymentId();
        this.timeSlotId    = command.timeSlotId();
    }

    public Long getId() { return id; }
    public Long getPatientId() { return patientId; }
    public Long getPsycologistId() { return psycologistId; }
    public Long getPaymentId() { return paymentId; }
    public Long getTimeSlotId() { return timeSlotId; }
}
