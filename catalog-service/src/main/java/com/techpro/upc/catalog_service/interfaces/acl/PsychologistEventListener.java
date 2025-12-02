package com.techpro.upc.catalog_service.interfaces.acl;

import com.techpro.upc.catalog_service.domain.model.entities.Psychologist;
import com.techpro.upc.catalog_service.infrastructure.configuration.RabbitMqConfig;
import com.techpro.upc.catalog_service.infrastructure.persistence.jpa.repositories.PsychologistRepository;
import com.techpro.upc.catalog_service.interfaces.acl.events.PsychologistProfileUpdatedEventDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PsychologistEventListener {

    private final PsychologistRepository psychologistRepository;

    public PsychologistEventListener(PsychologistRepository psychologistRepository) {
        this.psychologistRepository = psychologistRepository;
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void handlePsychologistUpdate(PsychologistProfileUpdatedEventDTO event) {
        System.out.println("Catalog: Recibida actualización para Psicólogo ID Externo: " + event.getPsychologistId());

        // Buscamos si ya lo tenemos en el catálogo por su ID de perfil original
        var optionalPsychologist = psychologistRepository.findByProfileId(event.getPsychologistId());

        if (optionalPsychologist.isPresent()) {
            // ACTUALIZAR
            var psychologist = optionalPsychologist.get();
            psychologist.setFirstName(event.getFirstName());
            psychologist.setLastName(event.getLastName());
            psychologist.setSpecialization(event.getSpecialization());
            psychologist.setGender(event.getGender());
            psychologist.setPhone(event.getPhone());
            psychologist.setCmp(event.getCmp());
            psychologistRepository.save(psychologist);
        } else {
            // CREAR (Primera vez que llena su perfil)
            var newPsychologist = new Psychologist(
                    event.getPsychologistId(),
                    event.getUserId(),
                    event.getFirstName(),
                    event.getLastName(),
                    event.getSpecialization(),
                    event.getGender(),
                    event.getPhone(),
                    event.getCmp()
            );
            psychologistRepository.save(newPsychologist);
        }
    }
}
