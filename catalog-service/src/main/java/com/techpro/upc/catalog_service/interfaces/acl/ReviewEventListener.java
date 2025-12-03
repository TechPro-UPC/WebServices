package com.techpro.upc.catalog_service.interfaces.acl;

import com.techpro.upc.catalog_service.infrastructure.configuration.RabbitMqConfig;
import com.techpro.upc.catalog_service.infrastructure.persistence.jpa.repositories.PsychologistRepository;
import com.techpro.upc.catalog_service.interfaces.acl.events.PsychologistReviewedEventDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewEventListener {

    private final PsychologistRepository psychologistRepository;

    public ReviewEventListener(PsychologistRepository psychologistRepository) {
        this.psychologistRepository = psychologistRepository;
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void handleReviewEvent(PsychologistReviewedEventDTO event) {
        System.out.println("Catalog: Nueva reseña recibida (" + event.getStars() + " estrellas) para psicólogo " + event.getPsychologistId());

        var optionalPsychologist = psychologistRepository.findByProfileId(event.getPsychologistId());

        if (optionalPsychologist.isPresent()) {
            var psychologist = optionalPsychologist.get();

            // 1. Obtener valores actuales (manejando nulos por si acaso)
            double currentRating = psychologist.getRating() != null ? psychologist.getRating() : 0.0;
            int currentCount = psychologist.getReviewCount() != null ? psychologist.getReviewCount() : 0;

            // 2. Calcular nuevo promedio acumulado
            // Fórmula: (PromedioActual * CantidadActual + NuevasEstrellas) / (CantidadActual + 1)
            double newTotal = (currentRating * currentCount) + event.getStars();
            int newCount = currentCount + 1;
            double newRating = newTotal / newCount;

            // Redondear a 2 decimales (opcional, para que se vea bonito)
            newRating = Math.round(newRating * 100.0) / 100.0;

            // 3. Guardar
            psychologist.setRating(newRating);
            psychologist.setReviewCount(newCount);

            psychologistRepository.save(psychologist);
            System.out.println("Catalog: Rating actualizado a " + newRating + " (" + newCount + " reseñas)");
        } else {
            System.err.println("Catalog: Error - Se recibió reseña para un psicólogo que no existe en el catálogo (ID: " + event.getPsychologistId() + ")");
        }
    }
}
