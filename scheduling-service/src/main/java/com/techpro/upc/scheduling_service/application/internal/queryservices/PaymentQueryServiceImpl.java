package com.techpro.upc.scheduling_service.application.internal.queryservices;


import com.techpro.upc.scheduling_service.domain.model.aggregates.Payment;
import com.techpro.upc.scheduling_service.domain.model.queries.GetAllPaymentsQuery;
import com.techpro.upc.scheduling_service.domain.model.queries.GetPaymentByIdQuery;
import com.techpro.upc.scheduling_service.domain.services.PaymentQueryService;
import com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {
    private final PaymentRepository paymentRepository;

    public PaymentQueryServiceImpl(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Optional<Payment> handle(GetPaymentByIdQuery query){
        return paymentRepository.findById(query.id());
    }

    @Override
    public List<Payment> handle(GetAllPaymentsQuery query){
        return paymentRepository.findAll();
    }
}
