package com.techpro.upc.catalog_service.infrastructure.scheduling;


import com.techpro.upc.catalog_service.infrastructure.scheduling.resources.TimeSlotResource;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


public interface SchedulingClient {

    @GetMapping("/api/v1/time-slots")
    List<TimeSlotResource> getAllTimeSlots();
}