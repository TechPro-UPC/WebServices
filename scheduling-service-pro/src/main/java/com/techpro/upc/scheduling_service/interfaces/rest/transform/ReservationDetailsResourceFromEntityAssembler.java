package com.techpro.upc.scheduling_service.interfaces.rest.transform;

import com.techpro.upc.scheduling_service.application.internal.outboundservices.profiles.PsychologistContextFacade;
import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import com.techpro.upc.scheduling_service.domain.model.queries.GetTimeSlotByIdQuery;
import com.techpro.upc.scheduling_service.domain.services.TimeSlotQueryService;
import com.techpro.upc.scheduling_service.interfaces.rest.acl.PsycologistDto;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.ReservationDetailsResource;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.TimeSlotResource;

/**
 * Assembler para construir el recurso detallado de una Reservation.
 */
public class ReservationDetailsResourceFromEntityAssembler {

    public static ReservationDetailsResource toResourceFromEntity(
            Reservation reservation,
            PsychologistContextFacade psychologistContextFacade,
            TimeSlotQueryService timeSlotQueryService) {

        // === 1. Obtener Datos del Psicólogo (Externo) ===
        PsycologistDto psycologistDto = null;
        try {
            var psychologist = psychologistContextFacade.fetchPsychologistById(reservation.getPsycologistId());

            if (psychologist != null) {
                // Se asume que tu PsycologistDto tiene constructor para (id, nombre, apellido, licencia)
                // Si psychologist es un Record usa .firstName(), si es Clase usa .getFirstName()
                psycologistDto = new PsycologistDto(
                        psychologist.id(),
                        psychologist.firstName(),
                        psychologist.lastName(),
                        psychologist.licenseNumber()
                );
            }
        } catch (Exception e) {
            // Log para debug, pero no detenemos la ejecución
            System.out.println("⚠️ Error fetching psychologist details: " + e.getMessage());
        }

        // === 2. Obtener Datos del TimeSlot (Interno) ===

        // CORRECCIÓN CRÍTICA: Aquí estaba el error. Usamos .getTimeSlotId()
        var timeSlotQuery = new GetTimeSlotByIdQuery(reservation.getTimeSlotId());

        var timeSlotResult = timeSlotQueryService.handle(timeSlotQuery);

        TimeSlotResource timeSlotResource = null;

        if (timeSlotResult.isPresent()) {
            timeSlotResource = TimeSlotResourceFromEntityAssembler.toResourceFromEntity(timeSlotResult.get());
        } else {
            // Log para saber qué ID intentó buscar realmente
            System.out.println("⚠️ Warning: TimeSlot not found inside Assembler. ID buscado: " + reservation.getTimeSlotId());
        }

        // === 3. Construir el Recurso Final ===
        return new ReservationDetailsResource(
                reservation.getId(),
                reservation.getPatientId(),
                psycologistDto,   // Será null si falló el servicio externo
                timeSlotResource, // Será null si no existe el horario, pero NO dará error 500
                reservation.getPaymentId()
        );
    }
}