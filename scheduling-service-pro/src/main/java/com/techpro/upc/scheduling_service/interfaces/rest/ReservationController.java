package com.techpro.upc.scheduling_service.interfaces.rest;

import com.techpro.upc.scheduling_service.application.internal.outboundservices.profiles.PsychologistContextFacade;
import com.techpro.upc.scheduling_service.domain.model.queries.GetAllReservationsQuery;
import com.techpro.upc.scheduling_service.domain.model.queries.GetReservationByIdQuery;
import com.techpro.upc.scheduling_service.domain.services.PaymentQueryService;
import com.techpro.upc.scheduling_service.domain.services.ReservationCommandService;
import com.techpro.upc.scheduling_service.domain.services.ReservationQueryService;
import com.techpro.upc.scheduling_service.domain.services.TimeSlotQueryService;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.CreateReservationResource;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.ReservationDetailsResource;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.ReservationResource;
import com.techpro.upc.scheduling_service.interfaces.rest.transform.CreateReservationCommandFromResourceAssembler;
import com.techpro.upc.scheduling_service.interfaces.rest.transform.ReservationDetailsResourceFromEntityAssembler;
import com.techpro.upc.scheduling_service.interfaces.rest.transform.ReservationResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Reservations", description = "Available Reservation Endpoints")
public class ReservationController {

    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;
    private final PsychologistContextFacade psychologistContextFacade;
    private final TimeSlotQueryService timeSlotQueryService;
    private final PaymentQueryService paymentQueryService;

    public ReservationController(ReservationCommandService reservationCommandService,
                                 ReservationQueryService reservationQueryService,
                                 PsychologistContextFacade psychologistContextFacade,
                                 TimeSlotQueryService timeSlotQueryService,
                                 PaymentQueryService paymentQueryService) {
        this.reservationCommandService = reservationCommandService;
        this.reservationQueryService = reservationQueryService;
        this.psychologistContextFacade = psychologistContextFacade;
        this.timeSlotQueryService = timeSlotQueryService;
        this.paymentQueryService = paymentQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<ReservationResource> createReservation(@RequestBody CreateReservationResource resource) {
        var command = CreateReservationCommandFromResourceAssembler.toCommandFromResource(resource);
        var reservation = reservationCommandService.handle(command);
        if (reservation.isEmpty()) return ResponseEntity.badRequest().build();
        var reservationResource = ReservationResourceFromEntityAssembler.toResourceFromEntity(reservation.get());
        return new ResponseEntity<>(reservationResource, HttpStatus.CREATED);
    }

    @GetMapping("/{reservationId}")
    @Operation(summary = "Get a reservation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation found"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationResource> getReservationById(@PathVariable Long reservationId) {
        var query = new GetReservationByIdQuery(reservationId);
        var reservation = reservationQueryService.handle(query);
        if (reservation.isEmpty()) return ResponseEntity.notFound().build();
        var reservationResource = ReservationResourceFromEntityAssembler.toResourceFromEntity(reservation.get());
        return ResponseEntity.ok(reservationResource);
    }

    @GetMapping
    @Operation(summary = "Get all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations found"),
            @ApiResponse(responseCode = "404", description = "Reservations not found")
    })
    public ResponseEntity<List<ReservationResource>> getAllReservations() {
        var query = new GetAllReservationsQuery();
        var reservations = reservationQueryService.handle(query);
        if (reservations.isEmpty()) return ResponseEntity.notFound().build();
        var resources = reservations.stream()
                .map(ReservationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Endpoint for detailed reservation info (aggregating external data).
     * Note: Changed path to avoid conflict and follow REST best practices.
     */
    @GetMapping("/{reservationId}/details")
    @Operation(summary = "Get detailed reservation information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation details found"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationDetailsResource> getReservationDetails(@PathVariable Long reservationId) {
        var query = new GetReservationByIdQuery(reservationId);
        var reservation = reservationQueryService.handle(query);

        if (reservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var detailsResource = ReservationDetailsResourceFromEntityAssembler.toResourceFromEntity(
                reservation.get(),
                psychologistContextFacade,
                timeSlotQueryService,
                paymentQueryService
        );

        return ResponseEntity.ok(detailsResource);
    }

    @GetMapping("/details")
    @Operation(summary = "Get detailed information for all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation details found"),
            @ApiResponse(responseCode = "404", description = "No reservations found")
    })
    public ResponseEntity<List<ReservationDetailsResource>> getAllReservationDetails() {
        var reservations = reservationQueryService.handle(new GetAllReservationsQuery());
        if (reservations.isEmpty()) return ResponseEntity.notFound().build();

        var resources = reservations.stream()
                .map(res -> ReservationDetailsResourceFromEntityAssembler.toResourceFromEntity(
                        res,
                        psychologistContextFacade,
                        timeSlotQueryService,
                        paymentQueryService
                ))
                .toList();

        return ResponseEntity.ok(resources);
    }
}
