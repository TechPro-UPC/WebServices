package com.techpro.upc.scheduling_service.interfaces.rest.transform;

import com.techpro.upc.scheduling_service.application.internal.outboundservices.profiles.PsychologistContextFacade;
import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import com.techpro.upc.scheduling_service.domain.model.queries.GetTimeSlotByIdQuery;
import com.techpro.upc.scheduling_service.domain.services.TimeSlotQueryService;
import com.techpro.upc.scheduling_service.interfaces.rest.acl.PsycologistDto;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.ReservationDetailsResource;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.TimeSlotResource; // Asegúrate de tener este import

/**
 * Assembler para construir el recurso detallado de una Reservation.
 */
public class ReservationDetailsResourceFromEntityAssembler {

    public static ReservationDetailsResource toResourceFromEntity(
            Reservation reservation,
            PsychologistContextFacade psychologistContextFacade,
            TimeSlotQueryService timeSlotQueryService) {

        // === 1. Obtener Datos del Psicólogo (Externo) ===
        // Manejo defensivo por si el psicólogo no existe (opcional pero recomendado)
        PsycologistDto psycologistDto = null;
        try {
            var psychologist = psychologistContextFacade.fetchPsychologistById(reservation.getPsycologistId());
            if (psychologist != null) {
                psycologistDto = new PsycologistDto(
                        psychologist.id(),
                        null,
                        psychologist.licenseNumber()
                );
            }
        } catch (Exception e) {
            // Loguear error si es necesario, pero permitir continuar
            System.out.println("Error fetching psychologist: " + e.getMessage());
        }

        // === 2. Obtener Datos del TimeSlot (Interno) ===
        var timeSlotQuery = new GetTimeSlotByIdQuery(reservation.getTimeSlotId());
        var timeSlotResult = timeSlotQueryService.handle(timeSlotQuery);

        // ARREGLO PRINCIPAL:
        // En lugar de lanzar excepción (throw), inicializamos en null.
        TimeSlotResource timeSlotResource = null;

        if (timeSlotResult.isPresent()) {
            timeSlotResource = TimeSlotResourceFromEntityAssembler.toResourceFromEntity(timeSlotResult.get());
        } else {
            // Opcional: Imprimir en consola para depuración sin romper la API
            System.out.println("⚠️ Warning: TimeSlot not found for Reservation ID: " + reservation.getId());
        }

        // === 3. Construir el Recurso Final ===
        return new ReservationDetailsResource(
                reservation.getId(),
                reservation.getPatientId(),
                psycologistDto,   // Puede ser null si falló el facade
                timeSlotResource, // Será null si no se encontró, pero NO dará error 500
                reservation.getPaymentId()
        );
    }
}