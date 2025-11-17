package com.techpro.upc.reviews_service.domain.model.services;

import com.techpro.upc.reviews_service.domain.model.aggregates.Review;
import com.techpro.upc.reviews_service.domain.model.commands.CreateReviewCommand;
import com.techpro.upc.reviews_service.domain.model.commands.DeleteReviewCommand;

import java.util.Optional;

/**
 * Command service for handling write operations on the Review aggregate.
 */
public interface ReviewCommandService {
    Optional<Review> handle(CreateReviewCommand command);
    void handle(DeleteReviewCommand command);
}
