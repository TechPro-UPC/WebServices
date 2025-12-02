package com.techpro.upc.profiles_service.application.internal.commandservices;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.domain.model.commands.CreatePatientCommand;
import com.techpro.upc.profiles_service.domain.model.commands.UpdatePatientCommand;
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

    // Eliminamos IamClient y PsychologistRepository porque el perfil ya fue creado por RabbitMQ
    public PatientCommandServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Optional<Patient> handle(UpdatePatientCommand command) {
        // 1. Buscamos el perfil "cascarón" que creó RabbitMQ
        var result = patientRepository.findById(command.id());

        if (result.isEmpty()) {
            throw new IllegalArgumentException("El perfil de paciente con ID " + command.id() + " no existe.");
        }

        var patientToUpdate = result.get();

        // 2. Validar que el DNI no esté siendo usado por OTRO paciente
        var patientWithSameDni = patientRepository.findByDni(command.dni());
        if (patientWithSameDni.isPresent() && !patientWithSameDni.get().getId().equals(command.id())) {
            throw new IllegalArgumentException("El DNI " + command.dni() + " ya está registrado por otro paciente.");
        }

        // 3. Actualizamos los datos usando el metodo de dominio
        patientToUpdate.updateProfile(
                command.firstName(),
                command.lastName(),
                command.dni(),
                command.phone(),
                command.gender()
        );

        // 4. Guardamos los cambios
        var updatedPatient = patientRepository.save(patientToUpdate);
        return Optional.of(updatedPatient);
    }
}
