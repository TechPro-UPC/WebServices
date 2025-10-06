package com.techpro.upc.profiles_service.domain.services;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.domain.model.commands.CreatePatientCommand;

import java.util.Optional;

public interface PatientCommandService {
    Optional<Patient> handle(CreatePatientCommand command);
}

