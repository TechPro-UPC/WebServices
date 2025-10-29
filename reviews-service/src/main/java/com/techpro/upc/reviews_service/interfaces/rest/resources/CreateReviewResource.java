package com.techpro.upc.reviews_service.interfaces.rest.resources;

/**
 * Resource representing the payload to create a new review.
 */
public record CreateReviewResource(
        Long patientId,
        Long psychologistId,
        Integer rating,
        String comment
) { }
