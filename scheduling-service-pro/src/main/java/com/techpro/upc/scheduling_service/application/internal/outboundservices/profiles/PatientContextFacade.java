package com.techpro.upc.scheduling_service.application.internal.outboundservices.profiles;

import com.techpro.upc.scheduling_service.infrastructure.profile.PatientClient;
import org.springframework.stereotype.Service;

@Service
public class PatientContextFacade {
    private final PatientClient patientClient;

    public PatientContextFacade(PatientClient patientClient) {
        this.patientClient = patientClient;
    }

    public void fetchPatientById(Long patientId) {
        // Lógica idéntica a la del psicólogo: Si falla o es 404, lanza excepción
        var response = patientClient.getById(patientId);
        if (response == null) {
            throw new RuntimeException("Patient not found with ID: " + patientId);
        }
    }
}
