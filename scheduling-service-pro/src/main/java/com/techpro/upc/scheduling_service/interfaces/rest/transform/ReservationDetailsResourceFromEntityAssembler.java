package com.techpro.upc.scheduling_service.interfaces.rest.transform;

import com.techpro.upc.scheduling_service.application.internal.outboundservices.profiles.PsychologistContextFacade;
import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import com.techpro.upc.scheduling_service.domain.model.queries.GetPaymentByIdQuery;
import com.techpro.upc.scheduling_service.domain.model.queries.GetTimeSlotByIdQuery;
import com.techpro.upc.scheduling_service.domain.services.PaymentQueryService;
import com.techpro.upc.scheduling_service.domain.services.TimeSlotQueryService;
import com.techpro.upc.scheduling_service.interfaces.rest.acl.PsycologistDto;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.ReservationDetailsResource;

/**
 * Assembler para construir el recurso detallado de una Reservation (sin worker).
 */
public class ReservationDetailsResourceFromEntityAssembler {

    public static ReservationDetailsResource toResourceFromEntity(
            Reservation reservation,
            PsychologistContextFacade psychologistContextFacade,
            TimeSlotQueryService timeSlotQueryService,
            PaymentQueryService paymentQueryService
    ) {
        // === PSYCHOLOGIST ===
        var psychologist = psychologistContextFacade.fetchPsychologistById(reservation.getPsycologistId());

        var psycologistDto = new PsycologistDto(
                psychologist.id(),
                null, // email no disponible desde Profiles; podrías reemplazar con userId
                psychologist.licenseNumber()
        );

        // === TIME SLOT ===
        var timeSlotQuery = new GetTimeSlotByIdQuery(reservation.getTimeSlotId());
        var timeSlotResult = timeSlotQueryService.handle(timeSlotQuery);
        if (timeSlotResult.isEmpty()) {
            throw new IllegalArgumentException("TimeSlot not found");
        }
        var timeSlotResource = TimeSlotResourceFromEntityAssembler.toResourceFromEntity(timeSlotResult.get());

        // === PAYMENT ===
        var paymentQuery = new GetPaymentByIdQuery(reservation.getPaymentId());
        var paymentResult = paymentQueryService.handle(paymentQuery);
        if (paymentResult.isEmpty()) {
            throw new IllegalArgumentException("Payment not found");
        }
        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(paymentResult.get());

        // === BUILD RESOURCE ===
        return new ReservationDetailsResource(
                reservation.getId(),
                reservation.getPatientId(),   // ✅ cambiado de getClientId() a getPatientId()
                psycologistDto,
                paymentResource,
                timeSlotResource
        );
    }
}
