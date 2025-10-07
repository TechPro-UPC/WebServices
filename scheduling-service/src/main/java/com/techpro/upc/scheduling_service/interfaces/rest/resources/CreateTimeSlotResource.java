package com.techpro.upc.scheduling_service.interfaces.rest.resources;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public record CreateTimeSlotResource(LocalDateTime startTime, LocalDateTime endTime, boolean status, String type) {
}
