package com.techpro.upc.scheduling_service.interfaces.rest.transform;


import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.ReservationResource;

public class ReservationResourceFromEntityAssembler {

    public static ReservationResource toResourceFromEntity(Reservation reservation) {
        return new ReservationResource(
                reservation.getId(),
                reservation.getPatientId(),
                reservation.getPycologistId(),
                reservation.getPaymentId(),
                reservation.getTimeSlotId(),
                reservation.getWorkerId()
        );
    }
}
