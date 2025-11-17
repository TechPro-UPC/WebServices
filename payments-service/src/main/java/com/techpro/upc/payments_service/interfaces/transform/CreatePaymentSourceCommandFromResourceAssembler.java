package com.techpro.upc.payments_service.interfaces.transform;

import com.techpro.upc.payments_service.domain.model.commands.CreatePaymentSourceCommand;
import com.techpro.upc.payments_service.interfaces.rest.resources.CreatePaymentSourceResource;

public class CreatePaymentSourceCommandFromResourceAssembler {
    public static CreatePaymentSourceCommand toCommandFromResource (CreatePaymentSourceResource resource){
    return new CreatePaymentSourceCommand(resource.totalAmount(), resource.status());
    }
}
