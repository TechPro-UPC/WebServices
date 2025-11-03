package com.techpro.upc.profiles_service.interfaces.rest.transform;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import com.techpro.upc.profiles_service.interfaces.rest.resources.PsychologistResource;

public class PsychologistResourceFromEntityAssembler {
    public static PsychologistResource toResourceFromEntity(Psychologist entity) {
        return new PsychologistResource(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDni(),
                entity.getPhone(),
                entity.getGender(),
                entity.getLicenseNumber(),
                entity.getSpecialization(),
                entity.getUserId()
        );
    }
}
