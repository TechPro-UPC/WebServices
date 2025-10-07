package com.techpro.upc.scheduling_service.interfaces.rest.transform;


import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import com.techpro.upc.scheduling_service.domain.model.queries.GetPaymentByIdQuery;
import com.techpro.upc.scheduling_service.domain.model.queries.GetTimeSlotByIdQuery;
import com.techpro.upc.scheduling_service.domain.services.PaymentQueryService;
import com.techpro.upc.scheduling_service.domain.services.TimeSlotQueryService;
import com.techpro.upc.scheduling_service.interfaces.rest.acl.PsycologistDto;
import com.techpro.upc.scheduling_service.interfaces.rest.acl.WorkerDto;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.ReservationDetailsResource;

public class ReservationDetailsResourceFromEntityAssembler {

    public static ReservationDetailsResource toResourceFromEntity(Reservation reservation, ProviderContextFacade providerContextFacade, TimeSlotQueryService timeSlotQueryService, WorkerContextFacade workerContextFacade, PaymentQueryService paymentQueryService) {

        var pycologist = pycologistContextFacade.fetchPycologistById(reservation.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException("Psycologist not found"));
        var psycologistDto = new PsycologistDto(
                pycologist.getId(),
                pycologist.getUser().getEmail(),
                pycologist.getLicenseNumber()
        );

        var worker = workerContextFacade.fetchWorkerById(reservation.getWorkerId()).orElseThrow(() -> new IllegalArgumentException("Worker not found"));
        var workerDto = new WorkerDto(
                worker.getId(),
                worker.getName(),
                worker.getSpecialization()
        );

        var timeSlotQuery = new GetTimeSlotByIdQuery(reservation.getTimeSlotId());
        var timeSlotResult = timeSlotQueryService.handle(timeSlotQuery);
        if (timeSlotResult.isEmpty()) {
            throw new IllegalArgumentException("TimeSlot not found");
        }
        var timeSLotResource = TimeSlotResourceFromEntityAssembler.toResourceFromEntity(timeSlotResult.get());

        var paymentQuery = new GetPaymentByIdQuery(reservation.getPaymentId());
        var paymentResult = paymentQueryService.handle(paymentQuery);
        if (paymentResult.isEmpty()) throw new IllegalArgumentException("Payment not found");
        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(paymentResult.get());

        return new ReservationDetailsResource(
                reservation.getId(),
                reservation.getClientId(),
                psycologistDto,
                paymentResource,
                timeSLotResource,
                workerDto
        );
    }
}
