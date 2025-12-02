package com.techpro.upc.catalog_service.application.internal;

import com.techpro.upc.catalog_service.domain.model.entities.Psychologist;
import com.techpro.upc.catalog_service.domain.model.queries.GetAllPsychologistsQuery;
import com.techpro.upc.catalog_service.domain.model.queries.GetPsychologistsBySpecializationQuery;
import com.techpro.upc.catalog_service.domain.services.CatalogQueryService;
import com.techpro.upc.catalog_service.infrastructure.persistence.jpa.repositories.PsychologistRepository;
import com.techpro.upc.catalog_service.infrastructure.scheduling.SchedulingClient;
import com.techpro.upc.catalog_service.infrastructure.scheduling.resources.TimeSlotResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogQueryServiceImpl implements CatalogQueryService {

    private final SchedulingClient schedulingClient;
    private final PsychologistRepository psychologistRepository;

    public CatalogQueryServiceImpl(SchedulingClient schedulingClient,
                                   PsychologistRepository psychologistRepository) {
        this.psychologistRepository = psychologistRepository;
        this.schedulingClient = schedulingClient;
    }

    @Override
    public List<TimeSlotResource> handle() {
        return schedulingClient.getAllTimeSlots();
    }

    @Override
    public List<Psychologist> handle(GetAllPsychologistsQuery query) {
        return psychologistRepository.findAll();
    }

    @Override
    public List<Psychologist> handle(GetPsychologistsBySpecializationQuery query) {
        // Usamos el metodo de búsqueda que creamos en el Repositorio
        return psychologistRepository.findBySpecializationContainingIgnoreCase(query.specialization());
    }
}
