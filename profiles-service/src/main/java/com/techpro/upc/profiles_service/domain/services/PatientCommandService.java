package com.techpro.upc.profiles_service.domain.services;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.domain.model.commands.CreatePatientCommand;
import com.techpro.upc.profiles_service.domain.model.commands.UpdatePatientCommand;

import java.util.Optional;

public interface PatientCommandService {
    // Actualizamos la firma del metodo para aceptar el nuevo comando
    Optional<Patient> handle(UpdatePatientCommand command);
}

