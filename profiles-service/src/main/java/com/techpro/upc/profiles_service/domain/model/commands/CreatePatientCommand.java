package com.techpro.upc.profiles_service.domain.model.commands;

public record CreatePatientCommand(
        String firstName,
        String lastName,
        String dni,
        String phone,
        String gender,
        Long userId
) {}

