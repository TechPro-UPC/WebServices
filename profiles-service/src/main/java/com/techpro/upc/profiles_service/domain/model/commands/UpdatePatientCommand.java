package com.techpro.upc.profiles_service.domain.model.commands;

public record UpdatePatientCommand(Long id, String firstName, String lastName, String dni, String phone, String gender) {
}
