package com.techpro.upc.catalog_service.interfaces.rest;

import com.techpro.upc.catalog_service.domain.model.entities.Psychologist;
import com.techpro.upc.catalog_service.domain.model.queries.GetAllPsychologistsQuery;
import com.techpro.upc.catalog_service.domain.model.queries.GetPsychologistsBySpecializationQuery;
import com.techpro.upc.catalog_service.domain.services.CatalogQueryService;
import com.techpro.upc.catalog_service.infrastructure.scheduling.resources.TimeSlotResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@Tag(name = "Catalog", description = "Endpoints para buscar psicólogos y servicios")
public class CatalogController {

    private final CatalogQueryService catalogQueryService;

    public CatalogController(CatalogQueryService catalogQueryService) {
        this.catalogQueryService = catalogQueryService;
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<TimeSlotResource>> listAvailableSessions() {
        var sessions = catalogQueryService.handle();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/psychologists")
    @Operation(summary = "Listar psicólogos", description = "Obtiene todos los psicólogos o filtra por especialidad (ej. ?specialization=Clinica)")
    public ResponseEntity<List<Psychologist>> getAllOrFilter(
            @RequestParam(required = false) String specialization) {

        List<Psychologist> result;

        if (specialization != null && !specialization.isBlank()) {
            // Caso: Búsqueda con filtro
            var query = new GetPsychologistsBySpecializationQuery(specialization);
            result = catalogQueryService.handle(query);
        } else {
            // Caso: Traer todos
            var query = new GetAllPsychologistsQuery();
            result = catalogQueryService.handle(query);
        }

        return ResponseEntity.ok(result);
    }
}