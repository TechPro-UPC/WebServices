package com.techpro.upc.profiles_service.interfaces.rest.resources;

public record UpdatePatientResource(
        String firstName,
        String lastName,
        String dni,
        String phone,
        String gender
) {}
