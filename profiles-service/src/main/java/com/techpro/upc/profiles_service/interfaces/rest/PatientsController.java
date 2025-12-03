package com.techpro.upc.profiles_service.interfaces.rest;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.domain.model.queries.GetAllPatientsQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPatientByIdQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPatientByUserIdQuery;
import com.techpro.upc.profiles_service.domain.services.PatientCommandService;
import com.techpro.upc.profiles_service.domain.services.PatientQueryService;
import com.techpro.upc.profiles_service.interfaces.rest.resources.CreatePatientResource;
import com.techpro.upc.profiles_service.interfaces.rest.resources.PatientResource;
import com.techpro.upc.profiles_service.interfaces.rest.resources.UpdatePatientResource;
import com.techpro.upc.profiles_service.interfaces.rest.transform.CreatePatientCommandFromResourceAssembler;
import com.techpro.upc.profiles_service.interfaces.rest.transform.PatientResourceFromEntityAssembler;
import com.techpro.upc.profiles_service.interfaces.rest.transform.UpdatePatientCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    // --- CAMBIO: De POST (Create) a PUT (Update) ---
    @Operation(summary = "Update patient profile", description = "Completes the patient profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientResource> updatePatient(@PathVariable Long id, @Valid @RequestBody UpdatePatientResource resource) {

        var command = UpdatePatientCommandFromResourceAssembler.toCommandFromResource(id, resource);
        Optional<Patient> patient = patientCommandService.handle(command);

        return patient
                .map(value -> ResponseEntity.ok(PatientResourceFromEntityAssembler.toResourceFromEntity(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- Los métodos GET se mantienen igual ---

    @Operation(summary = "Get a patient by ID", description = "Retrieve a patient by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientResource> getPatientById(@PathVariable Long id) {
        var result = patientQueryService.handle(new GetPatientByIdQuery(id));
        return result.map(patient -> ResponseEntity.ok(PatientResourceFromEntityAssembler.toResourceFromEntity(patient)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all patients", description = "Retrieve all registered patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patients not found")
    })
    @GetMapping
    public ResponseEntity<List<PatientResource>> getAllPatients() {
        List<Patient> patients = patientQueryService.handle(new GetAllPatientsQuery());
        if (patients.isEmpty()) return ResponseEntity.notFound().build();
        var resources = patients.stream()
                .map(PatientResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Get patient by userId", description = "Retrieve the patient associated with a given user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<PatientResource> getPatientByUserId(@PathVariable Long userId) {
        var result = patientQueryService.handle(new GetPatientByUserIdQuery(userId));
        return result
                .map(patient -> ResponseEntity.ok(PatientResourceFromEntityAssembler.toResourceFromEntity(patient)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
