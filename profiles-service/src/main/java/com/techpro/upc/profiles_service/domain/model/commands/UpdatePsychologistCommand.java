package com.techpro.upc.profiles_service.domain.model.commands;

public record UpdatePsychologistCommand(Long id, String firstName, String lastName, String dni, String phone, String gender, String licenseNumber, String specialization) {
}
