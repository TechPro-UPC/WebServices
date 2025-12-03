package com.techpro.upc.profiles_service.interfaces.acl;

// Necesitas crear un DTO espejo del evento del IAM en este microservicio
// para deserializar el JSON.
import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import com.techpro.upc.profiles_service.infrastructure.configuration.RabbitMqConfig;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PatientRepository;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PsychologistRepository;
import com.techpro.upc.profiles_service.interfaces.acl.events.UserRegisteredEventDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ProfileRegistrationEventListener {

    private final PatientRepository patientRepository;
    private final PsychologistRepository psychologistRepository;

    public ProfileRegistrationEventListener(PatientRepository patientRepository, PsychologistRepository psychologistRepository) {
        this.patientRepository = patientRepository;
        this.psychologistRepository = psychologistRepository;
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void handleUserRegisteredEvent(UserRegisteredEventDTO event) {
        System.out.println("Profiles: Evento recibido para " + event.getRole());

        if ("PATIENT".equals(event.getRole())) {
            // Usamos el constructor mínimo
            Patient newPatient = new Patient(event.getUserId());
            patientRepository.save(newPatient);
            System.out.println("Profiles: Paciente creado con ID externo " + event.getUserId());
        }
        else if ("PSYCHOLOGIST".equals(event.getRole())) {
            // Usamos el constructor mínimo
            Psychologist newPsychologist = new Psychologist(event.getUserId());
            psychologistRepository.save(newPsychologist);
            System.out.println("Profiles: Psicólogo creado con ID externo " + event.getUserId());
        }
    }
}
