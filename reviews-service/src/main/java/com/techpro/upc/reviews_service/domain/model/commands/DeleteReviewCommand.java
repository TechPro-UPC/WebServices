package com.techpro.upc.reviews_service.domain.model.commands;

/**
 * Command object used to delete an existing Review aggregate.
 */
public record DeleteReviewCommand(Long id) {
    public DeleteReviewCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Review ID must be a positive non-null value.");
        }
    }
}
