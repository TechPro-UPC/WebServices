package com.techpro.upc.profiles_service.application.internal.commandservices;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.domain.model.commands.CreatePatientCommand;
import com.techpro.upc.profiles_service.domain.services.PatientCommandService;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PatientRepository;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PsychologistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientCommandServiceImpl implements PatientCommandService {

    private final PatientRepository patientRepository;
    private final PsychologistRepository psychologistRepository;



    public PatientCommandServiceImpl(PatientRepository patientRepository,
                                     PsychologistRepository psychologistRepository) {
        this.patientRepository = patientRepository;
        this.psychologistRepository = psychologistRepository;
    }

    @Override
    @Transactional
    public Optional<Patient> handle(CreatePatientCommand command) {


        // Confiamos en que el userId es válido gracias a la validación del JWT.

        // 1️⃣ Validar duplicado en pacientes
        if (patientRepository.findByUserId(command.userId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un paciente asociado a ese usuario.");
        }

        // 2️⃣ Validar duplicado cruzado (usuario es psicólogo)
        if (psychologistRepository.findByUserId(command.userId()).isPresent()) {
            throw new IllegalArgumentException("Ese usuario ya está registrado como psicólogo, no puede ser paciente.");
        }

        // 3️⃣ Crear y guardar
        var patient = new Patient(
                command.firstName(),
                command.lastName(),
                command.dni(),
                command.phone(),
                command.gender(),
                command.userId()
        );

        var saved = patientRepository.save(patient);
        return Optional.of(saved);
    }
}