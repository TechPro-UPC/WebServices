package com.techpro.upc.catalog_service.domain.services;

import com.techpro.upc.catalog_service.domain.model.entities.Psychologist;
import com.techpro.upc.catalog_service.domain.model.queries.GetAllPsychologistsQuery;
import com.techpro.upc.catalog_service.domain.model.queries.GetPsychologistsBySpecializationQuery;
import com.techpro.upc.catalog_service.infrastructure.scheduling.resources.TimeSlotResource;
import java.util.List;

public interface CatalogQueryService {
    List<TimeSlotResource> handle();
    List<Psychologist> handle(GetAllPsychologistsQuery query);
    List<Psychologist> handle(GetPsychologistsBySpecializationQuery query);
}

