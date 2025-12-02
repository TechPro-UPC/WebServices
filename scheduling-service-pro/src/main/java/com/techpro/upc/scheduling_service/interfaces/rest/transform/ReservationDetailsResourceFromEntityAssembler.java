package com.techpro.upc.scheduling_service.interfaces.rest.transform;

import com.techpro.upc.scheduling_service.application.internal.outboundservices.profiles.PsychologistContextFacade;
import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import com.techpro.upc.scheduling_service.domain.model.queries.GetPaymentByIdQuery;
import com.techpro.upc.scheduling_service.domain.model.queries.GetTimeSlotByIdQuery;
import com.techpro.upc.scheduling_service.domain.services.PaymentQueryService;
import com.techpro.upc.scheduling_service.domain.services.TimeSlotQueryService;
import com.techpro.upc.scheduling_service.interfaces.rest.acl.PsycologistDto;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.ReservationDetailsResource;

public class ReservationDetailsResourceFromEntityAssembler {

    public static ReservationDetailsResource toResourceFromEntity(
            Reservation reservation,
            PsychologistContextFacade psychologistContextFacade,
            TimeSlotQueryService timeSlotQueryService,
            PaymentQueryService paymentQueryService
    ) {
        // === 1. PSYCHOLOGIST (External Profile) ===
        // CORRECCIÓN: Agregamos la 'h' en getPsychologistId()
        var psychologistResource = psychologistContextFacade.fetchPsychologistById(reservation.getPsychologistId());

        // CORRECCIÓN: Usamos getters estándar (.getId, .getLicenseNumber)
        // Nota: Mantenemos tu clase PsycologistDto (sin h) si así la tienes creada,
        // pero idealmente deberías renombrarla también.
        var psycologistDto = new PsycologistDto(
                psychologistResource.getId(),
                null, // Email no disponible, enviamos null
                psychologistResource.getLicenseNumber()
        );

        // === 2. TIME SLOT (Internal) ===
        var timeSlotQuery = new GetTimeSlotByIdQuery(reservation.getTimeSlotId());
        var timeSlotResult = timeSlotQueryService.handle(timeSlotQuery);

        if (timeSlotResult.isEmpty()) {
            // Es mejor retornar null o manejarlo suavemente en lugar de romper todo el endpoint
            throw new IllegalArgumentException("TimeSlot not found for ID: " + reservation.getTimeSlotId());
        }
        var timeSlotResource = TimeSlotResourceFromEntityAssembler.toResourceFromEntity(timeSlotResult.get());

        // === 3. PAYMENT (Internal) ===
        var paymentQuery = new GetPaymentByIdQuery(reservation.getPaymentId());
        var paymentResult = paymentQueryService.handle(paymentQuery);

        if (paymentResult.isEmpty()) {
            // Si no hay pago (ej. es una reserva pendiente), podrías manejarlo, pero por ahora lanzamos error
            throw new IllegalArgumentException("Payment not found for ID: " + reservation.getPaymentId());
        }
        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(paymentResult.get());

        // === 4. BUILD FINAL RESOURCE ===
        return new ReservationDetailsResource(
                reservation.getId(),
                reservation.getPatientId(),
                psycologistDto,
                paymentResource,
                timeSlotResource
        );
    }
}
