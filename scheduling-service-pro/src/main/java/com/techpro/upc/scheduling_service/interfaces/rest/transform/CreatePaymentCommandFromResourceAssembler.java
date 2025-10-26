package com.techpro.upc.scheduling_service.interfaces.rest.transform;

import com.techpro.upc.scheduling_service.domain.model.commands.CreatePaymentCommand;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.CreatePaymentResource;

public class CreatePaymentCommandFromResourceAssembler {
    public static CreatePaymentCommand toCommandFromResource(CreatePaymentResource resource) {
        return new CreatePaymentCommand(
                resource.amount(),
                resource.currency(),
                resource.status()
        );
    }
}
