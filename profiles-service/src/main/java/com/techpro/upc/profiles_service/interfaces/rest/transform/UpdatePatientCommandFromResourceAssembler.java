package com.techpro.upc.profiles_service.interfaces.rest.transform;

import com.techpro.upc.profiles_service.domain.model.commands.UpdatePatientCommand;
import com.techpro.upc.profiles_service.interfaces.rest.resources.UpdatePatientResource;

public class UpdatePatientCommandFromResourceAssembler {
    public static UpdatePatientCommand toCommandFromResource(Long id, UpdatePatientResource resource) {
        return new UpdatePatientCommand(
                id,
                resource.firstName(),
                resource.lastName(),
                resource.dni(),
                resource.phone(),
                resource.gender()
        );
    }
}
