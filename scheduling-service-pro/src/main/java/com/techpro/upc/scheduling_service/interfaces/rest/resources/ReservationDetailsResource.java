package com.techpro.upc.scheduling_service.interfaces.rest.resources;

import com.techpro.upc.scheduling_service.interfaces.rest.acl.PsycologistDto;

/**
 * Detalle completo de una Reservation (sin worker).
 */
public record ReservationDetailsResource(
        Long id,
        Long patientId,
        PsycologistDto psycologist,
        TimeSlotResource timeSlot,
        Long paymentId
) {
}
