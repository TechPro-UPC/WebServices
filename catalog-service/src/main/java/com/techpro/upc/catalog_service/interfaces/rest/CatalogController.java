package com.techpro.upc.catalog_service.interfaces.rest;

import com.techpro.upc.catalog_service.domain.services.CatalogQueryService;
import com.techpro.upc.catalog_service.infrastructure.scheduling.resources.TimeSlotResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
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
}