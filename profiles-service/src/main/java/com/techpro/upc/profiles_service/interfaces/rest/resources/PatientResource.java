package com.techpro.upc.profiles_service.interfaces.rest.resources;

public record PatientResource(
        Long id,
        String firstName,
        String lastName,
        String dni,
        String phone,
        String gender,
        Long userId
) {}
