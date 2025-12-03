package com.techpro.upc.iam_service.interfaces.rest.transform;


import com.techpro.upc.iam_service.domain.model.aggregates.Role;
import com.techpro.upc.iam_service.domain.model.commands.SignUpCommand;
import com.techpro.upc.iam_service.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        Role roleEnum = Role.valueOf(resource.role());

        return new SignUpCommand(resource.email(), resource.password(), roleEnum);
    }
}
