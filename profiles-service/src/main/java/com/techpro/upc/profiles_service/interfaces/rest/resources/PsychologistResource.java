package com.techpro.upc.profiles_service.interfaces.rest.resources;

public record PsychologistResource(
        Long id,
        String firstName,
        String lastName,
        String dni,
        String phone,
        String gender,
        String licenseNumber,
        String specialization,
        Long userId
) {}
