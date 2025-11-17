package com.techpro.upc.payments_service.interfaces.rest.resources;

import com.techpro.upc.payments_service.domain.model.aggregates.PaymentSource;
import com.techpro.upc.payments_service.domain.valueObjects.Money;
import com.techpro.upc.payments_service.domain.valueObjects.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreatePaymentSourceResource(Money totalAmount, @Schema(type = "string", allowableValues = {"AUTHORIZED", "FAILED", "PENDING", "DONE"})
PaymentStatus status) {
    public CreatePaymentSourceResource {
        if (totalAmount == null) {
            throw new IllegalArgumentException("totalAmount cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("status cannot be null");
        }
    }
}
