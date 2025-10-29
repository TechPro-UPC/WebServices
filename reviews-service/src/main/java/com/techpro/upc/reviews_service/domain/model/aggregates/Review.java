package com.techpro.upc.reviews_service.domain.model.aggregates;

import com.techpro.upc.reviews_service.domain.model.commands.CreateReviewCommand;
import com.techpro.upc.reviews_service.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review extends AuditableAbstractAggregateRoot<Review> {

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private Long psychologistId;

    @Column(nullable = false, length = 1000)
    private String comment;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private boolean isRead = false;

    public Review(CreateReviewCommand command) {
        this.patientId = command.patientId();
        this.psychologistId = command.psychologistId();
        this.comment = command.comment();
        this.rating = command.rating();
        this.isRead = command.isRead();
    }
}
