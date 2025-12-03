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

    @Column(name = "psychologist_id")  // ✅ CORRECTO
    private Long psychologistId;

    @Column(nullable = false)
    private Long paymentId;

    @Column(nullable = false)
    private Long timeSlotId;




    protected Reservation() { } // requerido por JPA

    public Reservation(CreateReservationCommand command){
        this.patientId     = command.patientId();
        this.psychologistId = command.psychologistId();
        this.timeSlotId    = command.timeSlotId();
        this.patientId = command.patientId();
        this.paymentId = command.paymentId();
    }

    public Long getId() { return id; }
    public Long getPatientId() { return patientId; }
    public Long getPsycologistId() { return psychologistId; }
    public Long getTimeSlotId() { return timeSlotId; }
    public Long getPaymentId() { return paymentId; }

}
