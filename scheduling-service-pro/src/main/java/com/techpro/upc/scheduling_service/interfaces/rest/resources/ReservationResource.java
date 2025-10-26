package com.techpro.upc.scheduling_service.interfaces.rest.resources;

public record ReservationResource(
        Long id,
        Long patientId,
        Long psycologistId,
        Long paymentId,
        Long timeSlotId
) {}
