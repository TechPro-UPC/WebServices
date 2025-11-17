package com.techpro.upc.scheduling_service.application.internal.commandservices;


import com.techpro.upc.scheduling_service.domain.model.aggregates.Payment;
import com.techpro.upc.scheduling_service.domain.model.commands.CreatePaymentCommand;
import com.techpro.upc.scheduling_service.domain.services.PaymentCommandService;
import com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {
    private final PaymentRepository paymentRepository;

    public PaymentCommandServiceImpl(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Optional<Payment> handle(CreatePaymentCommand command){
        var payment = new Payment(command);
        paymentRepository.save(payment);
        return Optional.of(payment);
    }
}
