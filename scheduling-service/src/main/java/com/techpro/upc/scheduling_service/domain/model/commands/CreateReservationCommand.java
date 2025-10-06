package com.techpro.upc.scheduling_service.domain.model.commands;

public record CreateReservationCommand(
        Long patientId,
        Long psycologistId,
        Long paymentId,
        Long timeSlotId,
        Long workerId
) {
}
