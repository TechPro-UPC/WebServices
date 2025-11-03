package com.techpro.upc.profiles_service.interfaces.rest.transform;

import com.techpro.upc.profiles_service.domain.model.commands.CreatePsychologistCommand;
import com.techpro.upc.profiles_service.interfaces.rest.resources.CreatePsychologistResource;

public class CreatePsychologistCommandFromResourceAssembler {
    public static CreatePsychologistCommand toCommandFromResource(CreatePsychologistResource resource) {
        return new CreatePsychologistCommand(
                resource.firstName(),
                resource.lastName(),
                resource.dni(),
                resource.phone(),
                resource.gender(),
                resource.licenseNumber(),
                resource.specialization(),
                resource.userId()
        );
    }
}
