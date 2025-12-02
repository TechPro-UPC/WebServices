package com.techpro.upc.profiles_service.interfaces.rest.transform;

import com.techpro.upc.profiles_service.domain.model.commands.UpdatePsychologistCommand;
import com.techpro.upc.profiles_service.interfaces.rest.resources.UpdatePsychologistResource;

public class UpdatePsychologistCommandFromResourceAssembler {
    public static UpdatePsychologistCommand toCommandFromResource(Long id, UpdatePsychologistResource resource) {
        return new UpdatePsychologistCommand(
                id,
                resource.firstName(),
                resource.lastName(),
                resource.dni(),
                resource.phone(),
                resource.gender(),
                resource.licenseNumber(),
                resource.specialization()
        );
    }
}
