package com.techpro.upc.scheduling_service.interfaces.rest;

import com.techpro.upc.scheduling_service.domain.model.queries.GetAllTimeSlotsQuery;
import com.techpro.upc.scheduling_service.domain.model.queries.GetTimeSlotByIdQuery;
import com.techpro.upc.scheduling_service.domain.services.TimeSlotCommandService;
import com.techpro.upc.scheduling_service.domain.services.TimeSlotQueryService;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.CreateTimeSlotResource;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.TimeSlotResource;
import com.techpro.upc.scheduling_service.interfaces.rest.transform.CreateTimeSlotCommandFromResourceAssembler;
import com.techpro.upc.scheduling_service.interfaces.rest.transform.TimeSlotResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/time-slots", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Time Slots", description = "Available Time Slots Endpoints")
public class TimeSlotController {

    private final TimeSlotCommandService timeSlotCommandService;
    private final TimeSlotQueryService timeSlotQueryService;

    public TimeSlotController(TimeSlotCommandService timeSlotCommandService,
                              TimeSlotQueryService timeSlotQueryService) {
        this.timeSlotCommandService = timeSlotCommandService;
        this.timeSlotQueryService = timeSlotQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new Time Slot")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "TimeSlot created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<TimeSlotResource> createTimeSlot(@RequestBody CreateTimeSlotResource resource) {
        var createCmd = CreateTimeSlotCommandFromResourceAssembler.toCommandFromResource(resource);
        var timeSlot = timeSlotCommandService.handle(createCmd);
        if (timeSlot.isEmpty()) return ResponseEntity.badRequest().build();
        var timeSlotResource = TimeSlotResourceFromEntityAssembler.toResourceFromEntity(timeSlot.get());
        return new ResponseEntity<>(timeSlotResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Time Slot by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "TimeSlot found"),
            @ApiResponse(responseCode = "404", description = "TimeSlot not found")
    })
    public ResponseEntity<TimeSlotResource> getTimeSlotById(@PathVariable Long id) {
        var query = new GetTimeSlotByIdQuery(id);
        var result = timeSlotQueryService.handle(query);
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        var resource = TimeSlotResourceFromEntityAssembler.toResourceFromEntity(result.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    @Operation(summary = "Get all Time Slots")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "TimeSlots found"),
            @ApiResponse(responseCode = "404", description = "TimeSlots not found")
    })
    public ResponseEntity<List<TimeSlotResource>> getAllTimeSlots() {
        var timeSlots = timeSlotQueryService.handle(new GetAllTimeSlotsQuery());
        if (timeSlots.isEmpty()) return ResponseEntity.notFound().build();
        var resources = timeSlots.stream()
                .map(TimeSlotResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
