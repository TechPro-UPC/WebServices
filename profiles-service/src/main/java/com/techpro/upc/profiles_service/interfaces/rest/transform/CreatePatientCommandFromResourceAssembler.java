package com.techpro.upc.profiles_service.interfaces.rest.transform;

import com.techpro.upc.profiles_service.domain.model.commands.CreatePatientCommand;
import com.techpro.upc.profiles_service.interfaces.rest.resources.CreatePatientResource;

public class CreatePatientCommandFromResourceAssembler {
    public static CreatePatientCommand toCommandFromResource(CreatePatientResource resource) {
        return new CreatePatientCommand(
                resource.firstName(),
                resource.lastName(),
                resource.dni(),
                resource.phone(),
                resource.gender(),
                resource.userId()
        );
    }
}
