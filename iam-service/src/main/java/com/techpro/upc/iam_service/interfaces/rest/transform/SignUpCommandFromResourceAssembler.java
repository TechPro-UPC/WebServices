package com.techpro.upc.iam_service.interfaces.rest.transform;


import com.techpro.upc.iam_service.domain.model.commands.SignUpCommand;
import com.techpro.upc.iam_service.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.email(), resource.password());
    }
}