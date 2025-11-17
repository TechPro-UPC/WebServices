package com.techpro.upc.profiles_service.interfaces.rest;



import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import com.techpro.upc.profiles_service.domain.model.queries.GetPsychologistByUserIdQuery;
import com.techpro.upc.profiles_service.domain.services.PsychologistCommandService;
import com.techpro.upc.profiles_service.domain.services.PsychologistQueryService;
import com.techpro.upc.profiles_service.interfaces.rest.resources.CreatePsychologistResource;
import com.techpro.upc.profiles_service.interfaces.rest.resources.PsychologistResource;
import com.techpro.upc.profiles_service.interfaces.rest.transform.CreatePsychologistCommandFromResourceAssembler;
import com.techpro.upc.profiles_service.interfaces.rest.transform.PsychologistResourceFromEntityAssembler;
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

@Tag(name = "Psychologists", description = "Endpoints para gestión de perfiles de psicólogos")
@RestController
@RequestMapping(value = "/api/v1/psychologists", produces = APPLICATION_JSON_VALUE)
public class PsychologistController {

    private final PsychologistCommandService psychologistCommandService;
    private final PsychologistQueryService psychologistQueryService;

    public PsychologistController(PsychologistCommandService psychologistCommandService,
                                  PsychologistQueryService psychologistQueryService) {
        this.psychologistCommandService = psychologistCommandService;
        this.psychologistQueryService = psychologistQueryService;
    }

    @Operation(summary = "Create a new psychologist profile", description = "Registers a new psychologist profile linked to the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Psychologist created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request (e.g., profile already exists)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<PsychologistResource> createPsychologist(
            @Valid @RequestBody CreatePsychologistResource resource,
            @AuthenticationPrincipal Long userId // 2. 🎯 Inyectar el userId desde el Token
    ) {
        if (userId == null) {
            return ResponseEntity.status(401).build(); // No autorizado
        }

        // 3. Pasar el resource Y el userId al Assembler
        var command = CreatePsychologistCommandFromResourceAssembler.toCommandFromResource(resource, userId);
        Optional<Psychologist> psychologist = psychologistCommandService.handle(command);

        return psychologist
                .map(entity -> new ResponseEntity<>(PsychologistResourceFromEntityAssembler.toResourceFromEntity(entity), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get current psychologist profile", description = "Retrieve the psychologist profile of the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Psychologist profile found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Psychologist profile not found for this user")
    })
    @GetMapping("/me") // 4. 🎯 Endpoint seguro "/me"
    public ResponseEntity<PsychologistResource> getMyPsychologistProfile(
            @AuthenticationPrincipal Long userId // 5. Inyectado desde el token
    ) {
        if (userId == null) {
            return ResponseEntity.status(401).build(); // No autorizado
        }

        // 6. Usa el servicio de Query con el userId seguro
        var query = new GetPsychologistByUserIdQuery(userId);
        var result = psychologistQueryService.handle(query);

        return result
                .map(psychologist -> ResponseEntity.ok(PsychologistResourceFromEntityAssembler.toResourceFromEntity(psychologist)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
