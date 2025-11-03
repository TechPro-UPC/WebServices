package com.techpro.upc.iam_service.interfaces.rest.transform;


import com.techpro.upc.iam_service.domain.model.commands.SignInCommand;
import com.techpro.upc.iam_service.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.email(), signInResource.password());
    }
}