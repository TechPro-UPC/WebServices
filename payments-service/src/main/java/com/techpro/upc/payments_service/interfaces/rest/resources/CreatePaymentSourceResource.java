package com.techpro.upc.payments_service.interfaces.rest.resources;

import com.techpro.upc.payments_service.domain.model.aggregates.PaymentSource;
import com.techpro.upc.payments_service.domain.valueObjects.Money;
import com.techpro.upc.payments_service.domain.valueObjects.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record CreatePaymentSourceResource(
        Long userId,
        BigDecimal amount,
        String currency
) {
    public CreatePaymentSourceResource {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("currency cannot be null or empty");
        }
    }
}
