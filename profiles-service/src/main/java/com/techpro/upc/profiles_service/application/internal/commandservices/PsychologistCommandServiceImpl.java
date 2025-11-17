package com.techpro.upc.profiles_service.application.internal.commandservices;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import com.techpro.upc.profiles_service.domain.model.commands.CreatePsychologistCommand;
import com.techpro.upc.profiles_service.domain.services.PsychologistCommandService;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PatientRepository;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PsychologistRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PsychologistCommandServiceImpl implements PsychologistCommandService {

    private final PsychologistRepository psychologistRepository;
    private final PatientRepository patientRepository;


    public PsychologistCommandServiceImpl(
            PsychologistRepository psychologistRepository,
            PatientRepository patientRepository

    ) {
        this.psychologistRepository = psychologistRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public Optional<Psychologist> handle(CreatePsychologistCommand command) {

        // Confiamos en el userId del token.

        // 1️⃣ Validar duplicado dentro de Psychologists
        if (psychologistRepository.findByUserId(command.userId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un psicólogo asociado a ese usuario.");
        }

        // 2️⃣ Validar duplicado cruzado en Patients
        if (patientRepository.findByUserId(command.userId()).isPresent()) {
            throw new IllegalArgumentException("Ese usuario ya está registrado como paciente, no puede ser psicólogo.");
        }

        // 3️⃣ Validar DNI o licencia duplicada (Reglas de negocio correctas)
        if (psychologistRepository.findByDni(command.dni()).isPresent()) {
            throw new IllegalArgumentException("El DNI ya está registrado en otro psicólogo.");
        }
        if (psychologistRepository.findByLicenseNumber(command.licenseNumber()).isPresent()) {
            throw new IllegalArgumentException("El número de licencia ya está registrado.");
        }

        // 4️⃣ Crear y guardar
        var psychologist = new Psychologist(
                command.firstName(),
                command.lastName(),
                command.dni(),
                command.phone(),
                command.gender(),
                command.licenseNumber(),
                command.specialization(),
                command.userId()
        );

        var saved = psychologistRepository.save(psychologist);
        return Optional.of(saved);
    }
}
