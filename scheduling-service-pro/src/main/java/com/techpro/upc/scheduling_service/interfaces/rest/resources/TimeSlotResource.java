package com.techpro.upc.scheduling_service.interfaces.rest.resources;

import java.time.LocalDateTime;

public record TimeSlotResource(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
