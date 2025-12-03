package com.techpro.upc.scheduling_service.interfaces.rest.resources;

public record CreateReservationResource(
        Long patientId,
        Long psychologistId,
        Long timeSlotId,
        Long paymentId
) {}
