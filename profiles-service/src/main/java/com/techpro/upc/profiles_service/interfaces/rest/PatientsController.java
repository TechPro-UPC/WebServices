package com.techpro.upc.profiles_service.interfaces.rest;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.domain.model.queries.GetAllPatientsQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPatientByIdQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPatientByUserIdQuery;
import com.techpro.upc.profiles_service.domain.services.PatientCommandService;
import com.techpro.upc.profiles_service.domain.services.PatientQueryService;
import com.techpro.upc.profiles_service.interfaces.rest.resources.CreatePatientResource;
import com.techpro.upc.profiles_service.interfaces.rest.resources.PatientResource;
import com.techpro.upc.profiles_service.interfaces.rest.transform.CreatePatientCommandFromResourceAssembler;
import com.techpro.upc.profiles_service.interfaces.rest.transform.PatientResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/patients", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Patients", description = "Endpoints for patient management")
public class PatientsController {

    private final PatientCommandService patientCommandService;
    private final PatientQueryService patientQueryService;

    public PatientsController(PatientCommandService patientCommandService, PatientQueryService patientQueryService) {
        this.patientCommandService = patientCommandService;
        this.patientQueryService = patientQueryService;
    }

    @Operation(summary = "Create a new patient profile", description = "Registers a new patient profile linked to the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request (e.g., profile already exists)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<PatientResource> createPatient(
            @Valid @RequestBody CreatePatientResource resource,
            @AuthenticationPrincipal Long userId // Inyectado desde el token
    ) {
        if (userId == null) {
            return ResponseEntity.status(401).build(); // No autorizado
        }

        var command = CreatePatientCommandFromResourceAssembler.toCommandFromResource(resource, userId);
        Optional<Patient> patient = patientCommandService.handle(command);

        return patient
                .map(value -> new ResponseEntity<>(PatientResourceFromEntityAssembler.toResourceFromEntity(value), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get current patient profile", description = "Retrieve the patient profile of the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient profile found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Patient profile not found for this user")
    })
    @GetMapping("/me") // 1. 🎯 Endpoint seguro "/me"
    public ResponseEntity<PatientResource> getMyPatientProfile(
            @AuthenticationPrincipal Long userId // 2. Inyectado desde el token
    ) {
        if (userId == null) {
            return ResponseEntity.status(401).build(); // No autorizado
        }

        // 3. Usa el servicio de Query con el userId seguro
        var query = new GetPatientByUserIdQuery(userId);
        var result = patientQueryService.handle(query);

        return result
                .map(patient -> ResponseEntity.ok(PatientResourceFromEntityAssembler.toResourceFromEntity(patient)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
