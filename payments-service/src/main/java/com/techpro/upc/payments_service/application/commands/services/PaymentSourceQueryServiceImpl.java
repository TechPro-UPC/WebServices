package com.techpro.upc.payments_service.application.commands.services;

import com.techpro.upc.payments_service.domain.model.aggregates.PaymentSource;
import com.techpro.upc.payments_service.domain.model.queries.GetAllPaymentSource;
import com.techpro.upc.payments_service.domain.model.queries.GetPaymentSourceByIdQuery;
import com.techpro.upc.payments_service.domain.services.PaymentSourceQueryService;
import com.techpro.upc.payments_service.infrastructure.persistance.jpa.PaymentSourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentSourceQueryServiceImpl implements PaymentSourceQueryService {

    private final PaymentSourceRepository paymentSourceRepository;

    public PaymentSourceQueryServiceImpl(PaymentSourceRepository paymentSourceRepository) {
        this.paymentSourceRepository = paymentSourceRepository;
    }

    @Override
    public List<PaymentSource> handle(GetAllPaymentSource query) {
        return paymentSourceRepository.findAll();
    }

    @Override
    public Optional<PaymentSource> handle(GetPaymentSourceByIdQuery query) {
        return paymentSourceRepository.findById(query.id());
    }
}