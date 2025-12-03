package com.techpro.upc.reviews_service.domain.model.aggregates;

import com.techpro.upc.reviews_service.domain.model.commands.CreateReviewCommand;
import com.techpro.upc.reviews_service.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews") // Es buena práctica ponerle nombre a la tabla
@Getter
@Setter
@NoArgsConstructor
public class Review extends AuditableAbstractAggregateRoot<Review> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ⚠️ FALTABA ESTO: JPA necesita un ID obligatorio

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private Long psychologistId;

    @Column(nullable = false, length = 1000)
    private String comment;

    @Column(nullable = false)
    private Integer stars; // ⚠️ CAMBIO: Renombrado de 'rating' a 'stars' para coincidir con el Servicio

    @Column(nullable = false)
    private boolean isRead = false;

    public Review(CreateReviewCommand command) {
        this.patientId = command.patientId();
        this.psychologistId = command.psychologistId();
        this.comment = command.comment();
        this.stars = command.rating(); // Asignamos el valor del comando (aunque ahí se llame rating)
        this.isRead = false; // Por defecto no leída al crearla
    }
}
