// src/main/java/com/techpro/upc/scheduling_service/infrastructure/profile/resources/PatientResource.java
package com.techpro.upc.scheduling_service.infrastructure.profile.resources;

import lombok.Data;

@Data
public class PatientResource {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
}
