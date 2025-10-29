package com.techpro.upc.catalog_service.infrastructure.scheduling;


import com.techpro.upc.catalog_service.infrastructure.configuration.FeignClientInterceptor;
import com.techpro.upc.catalog_service.infrastructure.scheduling.resources.TimeSlotResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "scheduling-service",
        url = "${scheduling-service.url}",
        configuration = FeignClientInterceptor.class
)
public interface SchedulingClient {

    @GetMapping("/api/v1/time-slots")
    List<TimeSlotResource> getAllTimeSlots();
}