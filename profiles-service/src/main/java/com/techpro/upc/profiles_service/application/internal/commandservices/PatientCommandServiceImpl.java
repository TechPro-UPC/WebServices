package com.techpro.upc.profiles_service.application.internal.commandservices;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.domain.model.commands.CreatePatientCommand;
import com.techpro.upc.profiles_service.domain.services.PatientCommandService;
import com.techpro.upc.profiles_service.infrastructure.iam.IamClient;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientCommandServiceImpl implements PatientCommandService {

    private final PatientRepository patientRepository;
    private final IamClient iamClient;

    public PatientCommandServiceImpl(PatientRepository patientRepository, IamClient iamClient) {
        this.patientRepository = patientRepository;
        this.iamClient = iamClient;
    }

    @Override
    public Optional<Patient> handle(CreatePatientCommand command) {

        // 🔍 Validar que el usuario exista en el IAM service
        ResponseEntity<?> userResponse = iamClient.getUserById(command.userId());

        if (!userResponse.getStatusCode().is2xxSuccessful() || userResponse.getBody() == null) {
            throw new IllegalArgumentException("El usuario con ID " + command.userId() + " no existe en el IAM Service.");
        }

        // ✅ Crear y guardar el paciente
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