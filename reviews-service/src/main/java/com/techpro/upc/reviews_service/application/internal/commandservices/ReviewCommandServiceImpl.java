package com.techpro.upc.reviews_service.application.internal.commandservices;

import com.techpro.upc.reviews_service.domain.model.aggregates.Review;
import com.techpro.upc.reviews_service.domain.model.commands.CreateReviewCommand;
import com.techpro.upc.reviews_service.domain.model.commands.DeleteReviewCommand;
import com.techpro.upc.reviews_service.domain.model.services.ReviewCommandService;
import com.techpro.upc.reviews_service.infrastructure.persistence.jpa.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of ReviewCommandService that handles create and delete commands.
 */
@Service
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;

    public ReviewCommandServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Optional<Review> handle(CreateReviewCommand command) {
        // Prevent duplicate reviews for the same patient and psychologist
        var existing = reviewRepository.findByPsychologistIdAndPatientId(
                command.psychologistId(), command.patientId()
        );
        if (existing.isPresent()) {
            throw new IllegalArgumentException("A review for this psychologist and patient already exists.");
        }

        var review = new Review(command);
        reviewRepository.save(review);
        return Optional.of(review);
    }

    @Override
    public void handle(DeleteReviewCommand command) {
        if (!reviewRepository.existsById(command.id())) {
            throw new IllegalArgumentException("The review you are trying to delete does not exist.");
        }
        reviewRepository.deleteById(command.id());
    }
}
