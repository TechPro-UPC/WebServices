package com.techpro.upc.scheduling_service.domain.model.commands;

import java.time.LocalDateTime;

public record CreateTimeSlotCommand(
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long psychologistId
) {
}
