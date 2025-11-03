package com.techpro.upc.profiles_service.application.internal.commandservices;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.domain.model.commands.CreatePatientCommand;
import com.techpro.upc.profiles_service.domain.services.PatientCommandService;
import com.techpro.upc.profiles_service.infrastructure.iam.IamClient;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PatientRepository;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PsychologistRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientCommandServiceImpl implements PatientCommandService {

    private final PatientRepository patientRepository;
    private final PsychologistRepository psychologistRepository;
    private final IamClient iamClient;

    public PatientCommandServiceImpl(PatientRepository patientRepository,
                                     PsychologistRepository psychologistRepository,
                                     IamClient iamClient) {
        this.patientRepository = patientRepository;
        this.psychologistRepository = psychologistRepository;
        this.iamClient = iamClient;
    }

    @Override
    @Transactional
    public Optional<Patient> handle(CreatePatientCommand command) {

        // 1) Validar que el usuario exista en IAM
        try {
            var user = iamClient.getUserById(command.userId());
            if (user == null || user.id() == null) {
                throw new IllegalArgumentException("El usuario con ID " + command.userId() + " no existe en IAM.");
            }
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("El usuario con ID " + command.userId() + " no existe en IAM.");
        } catch (FeignException.Unauthorized e) {
            throw new IllegalStateException("IAM respondió 401. Verifica que Profiles esté propagando el Bearer token.");
        }

        // 2) Validar duplicado en pacientes
        if (patientRepository.findByUserId(command.userId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un paciente asociado a ese usuario.");
        }

        // (Opcional) duplicado por DNI si tu modelo lo requiere
        // if (patientRepository.findByDni(command.dni()).isPresent()) {
        //     throw new IllegalArgumentException("Ya existe un paciente con ese DNI.");
        // }

        // 3) Validar duplicado cruzado (usuario ya es psicólogo)
        if (psychologistRepository.findByUserId(command.userId()).isPresent()) {
            throw new IllegalArgumentException("Ese usuario ya está registrado como psicólogo, no puede ser paciente.");
        }

        // 4) Crear y guardar
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
