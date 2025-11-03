package com.techpro.upc.catalog_service.infrastructure.scheduling.resources;

import java.time.LocalDateTime;

public record TimeSlotResource(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime
) {}