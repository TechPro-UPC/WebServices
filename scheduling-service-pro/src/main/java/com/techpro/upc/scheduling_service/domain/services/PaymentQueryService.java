package com.techpro.upc.scheduling_service.domain.services;

import com.techpro.upc.scheduling_service.domain.model.aggregates.Payment;
import com.techpro.upc.scheduling_service.domain.model.queries.GetAllPaymentsQuery;
import com.techpro.upc.scheduling_service.domain.model.queries.GetPaymentByIdQuery;

import java.util.List;
import java.util.Optional;

public interface PaymentQueryService {
    Optional<Payment> handle(GetPaymentByIdQuery query);

    List<Payment> handle(GetAllPaymentsQuery query);
}