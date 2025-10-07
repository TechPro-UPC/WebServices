package com.techpro.upc.scheduling_service.domain.model.aggregates;

import com.techpro.upc.scheduling_service.domain.model.commands.CreateReservationCommand;
import com.techpro.upc.scheduling_service.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;

public class Reservation extends AuditableAbstractAggregateRoot<Reservation> {
    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private Long psycologistId;

    @Column(nullable = false)
    private Long paymentId;

    @Column(nullable = false, length = 32)
    private Long timeSlotId;

    @Column(nullable = false)
    private Long workerId;

    public Reservation() {}

    public Reservation(CreateReservationCommand command){
        this.patientId= command.patientId();
        this.psycologistId= command.psycologistId();
        this.paymentId= command.paymentId();
        this.timeSlotId = command.timeSlotId();
        this.workerId= command.workerId();
    }

    public Long getPatientId() { return patientId; }
    public Long getPsycologistId() { return psycologistId; }
    public Long getPaymentId() { return paymentId; }
    public Long getTimeSlotId() { return timeSlotId; }
    public Long getWorkerId() { return workerId; }
}
