package com.techpro.upc.scheduling_service.interfaces.rest.resources;

public record CreateReservationResource(
        Long patientId,
        Long providerId,
        Long paymentId,
        Long timeSlotId,
        Long workerId
) {}
