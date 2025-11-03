package com.techpro.upc.catalog_service.application.internal;

import com.techpro.upc.catalog_service.domain.services.CatalogQueryService;
import com.techpro.upc.catalog_service.infrastructure.scheduling.SchedulingClient;
import com.techpro.upc.catalog_service.infrastructure.scheduling.resources.TimeSlotResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogQueryServiceImpl implements CatalogQueryService {

    private final SchedulingClient schedulingClient;

    public CatalogQueryServiceImpl(SchedulingClient schedulingClient) {
        this.schedulingClient = schedulingClient;
    }

    @Override
    public List<TimeSlotResource> handle() {
        return schedulingClient.getAllTimeSlots();
    }
}
