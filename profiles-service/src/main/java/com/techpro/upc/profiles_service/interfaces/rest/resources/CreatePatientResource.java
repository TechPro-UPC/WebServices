package com.techpro.upc.profiles_service.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePatientResource(
        @NotBlank @Size(max = 50) String firstName,
        @NotBlank @Size(max = 50) String lastName,
        @NotBlank @Size(min = 8, max = 8) String dni,
        @Size(max = 20) String phone,
        @Size(max = 10) String gender

) {}
