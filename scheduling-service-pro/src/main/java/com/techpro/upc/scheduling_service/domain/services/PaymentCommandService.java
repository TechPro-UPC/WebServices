package com.techpro.upc.scheduling_service.domain.services;

import com.techpro.upc.scheduling_service.domain.model.aggregates.Payment;
import com.techpro.upc.scheduling_service.domain.model.commands.CreatePaymentCommand;

import java.util.Optional;

public interface PaymentCommandService {
    Optional<Payment> handle(CreatePaymentCommand command);
}
