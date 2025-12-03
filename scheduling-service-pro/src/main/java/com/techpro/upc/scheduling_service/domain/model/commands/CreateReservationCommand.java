package com.techpro.upc.scheduling_service.domain.model.commands;

public record CreateReservationCommand(
        Long patientId,
        Long psychologistId,
        Long paymentId,
        Long timeSlotId
) {}
