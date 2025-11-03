package com.techpro.upc.payments_service.interfaces.transform;

import com.techpro.upc.payments_service.domain.model.aggregates.PaymentSource;
import com.techpro.upc.payments_service.interfaces.rest.resources.PaymentSourceResource;

public class PaymentSourceResourceFromEntityAssembler {
    public static PaymentSourceResource toResourceFromEntity(PaymentSource entity){
        return new PaymentSourceResource(
                entity.getId(),
                entity.getTotalAmount(),
                entity.getStatus());
    }
}
