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

/**
 * ReservationController
 */
@RestController
@RequestMapping(value = "/api/v1/reservationsDetails", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Reservations", description = "Available Reservation Endpoints")
public class ReservationController {

    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;

    // Facades / servicios externos usados para armar los "details"
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

    /**
     * Create a new reservation
     */
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

    /**
     * Get a reservation by ID
     */
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

    /**
     * Get all reservations
     */
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
     * Get detailed reservation information (one)
     */
    @GetMapping("/details/{reservationId}")
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

    /**
     * Get all reservations with full detail
     */
    @GetMapping("/details")
    @Operation(
            summary = "Get detailed information for all reservations",
            description = "Returns every reservation along with provider, worker, time-slot and payment data"
    )
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
