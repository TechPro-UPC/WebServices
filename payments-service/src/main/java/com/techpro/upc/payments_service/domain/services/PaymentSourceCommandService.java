package com.techpro.upc.payments_service.domain.services;

import com.techpro.upc.payments_service.domain.model.aggregates.PaymentSource;
import com.techpro.upc.payments_service.domain.model.commands.CreatePaymentSourceCommand;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PaymentSourceCommandService {
    Optional<PaymentSource> handle (CreatePaymentSourceCommand command);
}
