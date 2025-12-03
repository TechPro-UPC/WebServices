// src/main/java/com/techpro/upc/scheduling_service/infrastructure/profile/resources/PsychologistResource.java
package com.techpro.upc.scheduling_service.infrastructure.profile.resources;

import lombok.Data;

@Data
public class PsychologistResource {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String licenseNumber; // <-- Faltaba este campo
    private String email;
}
