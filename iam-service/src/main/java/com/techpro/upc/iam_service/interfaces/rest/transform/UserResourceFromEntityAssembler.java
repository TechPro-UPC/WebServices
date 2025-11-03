package com.techpro.upc.iam_service.interfaces.rest.transform;


import com.techpro.upc.iam_service.domain.model.aggregates.User;
import com.techpro.upc.iam_service.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(user.getId(), user.getEmail());
    }
}