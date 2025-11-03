package com.techpro.upc.reviews_service.domain.model.commands;

/**
 * Command object used to create a new Review aggregate.
 * Immutable and validated upon creation.
 */
public record CreateReviewCommand(
        Long patientId,
        Long psychologistId,
        Integer rating,
        String comment,
        boolean isRead
) {
    public CreateReviewCommand {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (patientId == null || psychologistId == null) {
            throw new IllegalArgumentException("Patient and Psychologist IDs cannot be null");
        }
        if (comment == null || comment.isBlank()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
    }
}
