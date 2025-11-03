// src/main/java/com/techpro/upc/scheduling_service/infrastructure/profile/resources/PatientResource.java
package com.techpro.upc.scheduling_service.infrastructure.profile.resources;

public record PatientResource(
        Long id,
        String firstName,
        String lastName,
        String dni,
        String phone,
        String gender,
        Long userId
) {}
