package com.techpro.upc.reviews_service.domain.model.queries;

/**
 * Query object to retrieve all reviews written for a specific psychologist.
 */
public record GetReviewsByPsychologistIdQuery(Long psychologistId) {
    public GetReviewsByPsychologistIdQuery {
        if (psychologistId == null || psychologistId <= 0) {
            throw new IllegalArgumentException("Psychologist ID must be a positive, non-null value.");
        }
    }
}
