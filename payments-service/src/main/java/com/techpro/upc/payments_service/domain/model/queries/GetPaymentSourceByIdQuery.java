package com.techpro.upc.payments_service.domain.model.queries;

public record GetPaymentSourceByIdQuery(Long id) {
    public GetPaymentSourceByIdQuery {
        if (id == null){
            throw new NullPointerException("Id cannot be null");
        }
    }
}
