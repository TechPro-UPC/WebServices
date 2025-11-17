package com.techpro.upc.payments_service.domain.model.commands;

import com.techpro.upc.payments_service.domain.valueObjects.Money;
import com.techpro.upc.payments_service.domain.valueObjects.PaymentStatus;

import java.math.BigDecimal;

public record CreatePaymentSourceCommand(Money totalAmount, PaymentStatus status) {
}
