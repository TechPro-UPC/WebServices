package com.techpro.upc.payments_service.application.commands.services;

import com.techpro.upc.payments_service.domain.model.aggregates.PaymentSource;
import com.techpro.upc.payments_service.domain.model.commands.CreatePaymentSourceCommand;
import com.techpro.upc.payments_service.domain.model.queries.GetAllPaymentSource;
import com.techpro.upc.payments_service.domain.services.PaymentSourceCommandService;
import com.techpro.upc.payments_service.infrastructure.persistance.jpa.PaymentSourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentSourceCommandServiceImpl implements PaymentSourceCommandService {

    private final PaymentSourceRepository paymentSourceRepository;
    public PaymentSourceCommandServiceImpl(PaymentSourceRepository paymentSourceRepository) {
        this.paymentSourceRepository = paymentSourceRepository;
    }


    @Override
    public Optional<PaymentSource> handle (CreatePaymentSourceCommand command) {
        var paymentSource = new PaymentSource(command);
        var createdPaymentSource = paymentSourceRepository.save(paymentSource);
        return Optional.of(createdPaymentSource);

    }
}

