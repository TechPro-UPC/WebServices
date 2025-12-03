package com.techpro.upc.payments_service.interfaces.rest.resources;

import com.techpro.upc.payments_service.domain.valueObjects.Money;
import com.techpro.upc.payments_service.domain.valueObjects.PaymentStatus;

public record PaymentSourceResource(
        Long id,
        Long userId,
        Money totalAmount,
        PaymentStatus status
) {
}
