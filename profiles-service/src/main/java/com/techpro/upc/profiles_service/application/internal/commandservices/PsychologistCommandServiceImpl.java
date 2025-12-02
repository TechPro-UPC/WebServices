package com.techpro.upc.profiles_service.application.internal.commandservices;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import com.techpro.upc.profiles_service.domain.model.commands.CreatePsychologistCommand;
import com.techpro.upc.profiles_service.domain.model.commands.UpdatePsychologistCommand;
import com.techpro.upc.profiles_service.domain.model.events.PsychologistProfileUpdatedEvent;
import com.techpro.upc.profiles_service.domain.services.PsychologistCommandService;
import com.techpro.upc.profiles_service.infrastructure.iam.IamClient;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PatientRepository;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PsychologistRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PsychologistCommandServiceImpl implements PsychologistCommandService {

    private final PsychologistRepository psychologistRepository;
    private final ApplicationEventPublisher eventPublisher;

    // CORRECCIÓN: Agregamos 'ApplicationEventPublisher eventPublisher' en los parámetros
    public PsychologistCommandServiceImpl(PsychologistRepository psychologistRepository, ApplicationEventPublisher eventPublisher) {
        this.psychologistRepository = psychologistRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<Psychologist> handle(UpdatePsychologistCommand command) {

        // 1. Buscar el perfil "cascarón"
        var result = psychologistRepository.findById(command.id());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("El perfil de psicólogo con ID " + command.id() + " no existe.");
        }

        // 2. Validaciones de Negocio
        if (psychologistRepository.existsByDniAndIdNot(command.dni(), command.id())) {
            throw new IllegalArgumentException("El DNI " + command.dni() + " ya está registrado por otro psicólogo.");
        }
        if (psychologistRepository.existsByLicenseNumberAndIdNot(command.licenseNumber(), command.id())) {
            throw new IllegalArgumentException("El número de licencia " + command.licenseNumber() + " ya está registrado.");
        }

        var psychologistToUpdate = result.get();

        // 3. Actualizar datos
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

        // 5. 📢 PUBLICAR EVENTO (Para que Catalog Service se entere)
        var event = new PsychologistProfileUpdatedEvent(
                this,
                saved.getId(),
                saved.getUserId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getSpecialization(),
                saved.getGender(),
                saved.getPhone(),
                saved.getLicenseNumber()
        );
        eventPublisher.publishEvent(event);

        return Optional.of(saved);
    }
}
