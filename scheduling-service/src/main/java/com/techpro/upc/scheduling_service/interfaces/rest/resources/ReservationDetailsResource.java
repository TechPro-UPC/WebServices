package com.techpro.upc.scheduling_service.interfaces.rest.resources;

import com.techpro.upc.scheduling_service.interfaces.rest.acl.PsycologistDto;
import com.techpro.upc.scheduling_service.interfaces.rest.acl.WorkerDto;

public record ReservationDetailsResource(
        Long id,
        Long patientId,
        PsycologistDto psycologist,
        PaymentResource paymentId,
        TimeSlotResource timeSlot,
        WorkerDto workerId
) {
}
