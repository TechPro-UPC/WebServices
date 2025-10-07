package com.techpro.upc.profiles_service.application.internal.commandservices;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import com.techpro.upc.profiles_service.domain.model.commands.CreatePsychologistCommand;
import com.techpro.upc.profiles_service.domain.services.PsychologistCommandService;
import com.techpro.upc.profiles_service.infrastructure.iam.IamClient;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PatientRepository;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PsychologistRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PsychologistCommandServiceImpl implements PsychologistCommandService {

    private final PsychologistRepository psychologistRepository;
    private final PatientRepository patientRepository; // 👈 NUEVO
    private final IamClient iamClient;

    public PsychologistCommandServiceImpl(
            PsychologistRepository psychologistRepository,
            PatientRepository patientRepository, // 👈 INYECTAR AQUÍ
            IamClient iamClient
    ) {
        this.psychologistRepository = psychologistRepository;
        this.patientRepository = patientRepository;
        this.iamClient = iamClient;
    }

    @Override
    @Transactional
    public Optional<Psychologist> handle(CreatePsychologistCommand command) {

        // 1️⃣ Validar existencia del usuario en IAM
        ResponseEntity<?> userResponse = iamClient.getUserById(command.userId());
        if (!userResponse.getStatusCode().is2xxSuccessful() || userResponse.getBody() == null) {
            throw new IllegalArgumentException("El usuario con ID " + command.userId() + " no existe en el IAM Service.");
        }

        // 2️⃣ Validar duplicado dentro de Psychologists
        if (psychologistRepository.findByUserId(command.userId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un psicólogo asociado a ese usuario.");
        }

        // 3️⃣ Validar duplicado cruzado en Patients
        if (patientRepository.findByUserId(command.userId()).isPresent()) {
            throw new IllegalArgumentException("Ese usuario ya está registrado como paciente, no puede ser psicólogo.");
        }

        // 4️⃣ Validar DNI o licencia duplicada
        if (psychologistRepository.findByDni(command.dni()).isPresent()) {
            throw new IllegalArgumentException("El DNI ya está registrado en otro psicólogo.");
        }
        if (psychologistRepository.findByLicenseNumber(command.licenseNumber()).isPresent()) {
            throw new IllegalArgumentException("El número de licencia ya está registrado.");
        }

        // 5️⃣ Crear y guardar
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
