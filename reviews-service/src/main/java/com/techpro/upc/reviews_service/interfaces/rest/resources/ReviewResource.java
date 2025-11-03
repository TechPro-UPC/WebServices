package com.techpro.upc.reviews_service.interfaces.rest.resources;

/**
 * Resource representing a review entity exposed to clients.
 */
public record ReviewResource(
        Long id,
        Long patientId,
        Long psychologistId,
        Integer rating,
        String comment,
        boolean isRead
) { }
