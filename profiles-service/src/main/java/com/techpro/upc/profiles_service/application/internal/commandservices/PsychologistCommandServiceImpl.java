package com.techpro.upc.profiles_service.application.internal.commandservices;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import com.techpro.upc.profiles_service.domain.model.commands.CreatePsychologistCommand;
import com.techpro.upc.profiles_service.domain.model.commands.UpdatePsychologistCommand;
import com.techpro.upc.profiles_service.domain.services.PsychologistCommandService;
import com.techpro.upc.profiles_service.infrastructure.iam.IamClient;
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

    // Ya no necesitamos IamClient ni PatientRepository porque el perfil ya existe
    public PsychologistCommandServiceImpl(PsychologistRepository psychologistRepository) {
        this.psychologistRepository = psychologistRepository;
    }

    @Override
    public Optional<Psychologist> handle(UpdatePsychologistCommand command) {

        // 1. Buscar el perfil "cascarón" que creó RabbitMQ
        var result = psychologistRepository.findById(command.id());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("El perfil de psicólogo con ID " + command.id() + " no existe.");
        }

        // 2. Validaciones de Negocio (Duplicados)
        if (psychologistRepository.existsByDniAndIdNot(command.dni(), command.id())) {
            throw new IllegalArgumentException("El DNI " + command.dni() + " ya está registrado por otro psicólogo.");
        }
        if (psychologistRepository.existsByLicenseNumberAndIdNot(command.licenseNumber(), command.id())) {
            throw new IllegalArgumentException("El número de licencia " + command.licenseNumber() + " ya está registrado.");
        }

        var psychologistToUpdate = result.get();

        // 3. Actualizar datos usando el método de dominio
        psychologistToUpdate.updateProfile(
                command.firstName(),
                command.lastName(),
                command.dni(),
                command.phone(),
                command.gender(),
                command.licenseNumber(),
                command.specialization()
        );

        // 4. Guardar
        var saved = psychologistRepository.save(psychologistToUpdate);
        return Optional.of(saved);
    }
}
