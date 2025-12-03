package com.techpro.upc.payments_service.interfaces.transform;

import com.techpro.upc.payments_service.domain.model.commands.CreatePaymentSourceCommand;
import com.techpro.upc.payments_service.domain.valueObjects.Money;
import com.techpro.upc.payments_service.domain.valueObjects.PaymentStatus;
import com.techpro.upc.payments_service.interfaces.rest.resources.CreatePaymentSourceResource;

public class CreatePaymentSourceCommandFromResourceAssembler {

    public static CreatePaymentSourceCommand toCommandFromResource(CreatePaymentSourceResource resource) {
        // Construimos el Value Object 'Money' usando los datos planos
        var money = new Money(resource.amount(), resource.currency());

        return new CreatePaymentSourceCommand(
                resource.userId(),
                money,
                PaymentStatus.DONE // Simulamos que entra directo como completado
        );
    }
}
