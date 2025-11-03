package com.techpro.upc.payments_service.domain.valueObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Money {

    @Column(name = "amount", nullable = false)
    @Getter
    private final BigDecimal amount;

    @Column(name = "currency_code", nullable = false, length = 3) // Set a max length (ISO 4217 is 3 chars)
    @Getter
    private final String currency;

    public Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    protected Money() {
        this.amount = null;
        this.currency = null;
    }

}