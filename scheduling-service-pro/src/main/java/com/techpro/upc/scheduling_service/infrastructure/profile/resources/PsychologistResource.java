// src/main/java/com/techpro/upc/scheduling_service/infrastructure/profile/resources/PsychologistResource.java
package com.techpro.upc.scheduling_service.infrastructure.profile.resources;

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
