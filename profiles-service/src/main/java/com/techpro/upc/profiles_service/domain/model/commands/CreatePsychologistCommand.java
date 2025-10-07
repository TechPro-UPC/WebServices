package com.techpro.upc.profiles_service.domain.model.commands;

public record CreatePsychologistCommand(
        String firstName,
        String lastName,
        String dni,
        String phone,
        String gender,
        String licenseNumber,
        String specialization,
        Long userId
) {}
