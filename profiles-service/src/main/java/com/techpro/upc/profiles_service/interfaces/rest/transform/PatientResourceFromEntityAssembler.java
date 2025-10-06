package com.techpro.upc.profiles_service.interfaces.rest.transform;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.interfaces.rest.resources.PatientResource;

public class PatientResourceFromEntityAssembler {
    public static PatientResource toResourceFromEntity(Patient entity) {
        return new PatientResource(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDni(),
                entity.getPhone(),
                entity.getGender(),
                entity.getUserId()
        );
    }
}