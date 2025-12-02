package com.techpro.upc.profiles_service.interfaces.rest;

import com.techpro.upc.profiles_service.application.internal.commandservices.PsychologistCommandServiceImpl;
import com.techpro.upc.profiles_service.application.internal.queryservices.PsychologistQueryServiceImpl;
import com.techpro.upc.profiles_service.domain.model.queries.GetAllPsychologistsQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPsychologistByIdQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPsychologistByUserIdQuery;
import com.techpro.upc.profiles_service.interfaces.rest.resources.CreatePsychologistResource;
import com.techpro.upc.profiles_service.interfaces.rest.resources.PsychologistResource;
import com.techpro.upc.profiles_service.interfaces.rest.resources.UpdatePsychologistResource;
import com.techpro.upc.profiles_service.interfaces.rest.transform.CreatePsychologistCommandFromResourceAssembler;
import com.techpro.upc.profiles_service.interfaces.rest.transform.PsychologistResourceFromEntityAssembler;
import com.techpro.upc.profiles_service.interfaces.rest.transform.UpdatePsychologistCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Psychologists", description = "Endpoints para gestión de perfiles de psicólogos")
@RestController
@RequestMapping("/api/v1/psychologists")
public class PsychologistController {

    private final PsychologistCommandServiceImpl psychologistCommandService;
    private final PsychologistQueryServiceImpl psychologistQueryService;

    public PsychologistController(PsychologistCommandServiceImpl psychologistCommandService,
                                  PsychologistQueryServiceImpl psychologistQueryService) {
        this.psychologistCommandService = psychologistCommandService;
        this.psychologistQueryService = psychologistQueryService;
    }

    // --- CAMBIO: De POST a PUT ---
    @Operation(summary = "Update psychologist profile", description = "Completes the psychologist profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Psychologist updated successfully"),
            @ApiResponse(responseCode = "404", description = "Psychologist not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PsychologistResource> updatePsychologist(@PathVariable Long id, @Valid @RequestBody UpdatePsychologistResource resource) {
        // Transformamos el resource + ID al comando de actualización
        var command = UpdatePsychologistCommandFromResourceAssembler.toCommandFromResource(id, resource);

        var psychologist = psychologistCommandService.handle(command);

        return psychologist
                .map(entity -> ResponseEntity.ok(PsychologistResourceFromEntityAssembler.toResourceFromEntity(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- GETs se mantienen igual ---

    @GetMapping
    public List<PsychologistResource> getAllPsychologists() {
        var psychologists = psychologistQueryService.handle(new GetAllPsychologistsQuery());
        return psychologists.stream()
                .map(PsychologistResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PsychologistResource> getPsychologistById(@PathVariable Long id) {
        var query = new GetPsychologistByIdQuery(id);
        var psychologist = psychologistQueryService.handle(query);
        return psychologist
                .map(entity -> ResponseEntity.ok(PsychologistResourceFromEntityAssembler.toResourceFromEntity(entity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PsychologistResource> getPsychologistByUserId(@PathVariable Long userId) {
        var query = new GetPsychologistByUserIdQuery(userId);
        var psychologist = psychologistQueryService.handle(query);
        return psychologist
                .map(entity -> ResponseEntity.ok(PsychologistResourceFromEntityAssembler.toResourceFromEntity(entity)))
                .orElse(ResponseEntity.notFound().build());
    }
}
