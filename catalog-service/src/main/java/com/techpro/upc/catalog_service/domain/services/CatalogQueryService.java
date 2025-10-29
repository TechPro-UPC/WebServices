package com.techpro.upc.catalog_service.domain.services;

import com.techpro.upc.catalog_service.infrastructure.scheduling.resources.TimeSlotResource;
import java.util.List;

public interface CatalogQueryService {
    List<TimeSlotResource> handle();
}

