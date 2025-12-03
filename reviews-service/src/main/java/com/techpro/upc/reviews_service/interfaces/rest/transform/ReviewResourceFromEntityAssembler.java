package com.techpro.upc.reviews_service.interfaces.rest.transform;

import com.techpro.upc.reviews_service.domain.model.aggregates.Review;
import com.techpro.upc.reviews_service.interfaces.rest.resources.ReviewResource;

/**
 * Converts domain entities to REST resources for external representation.
 */
public class ReviewResourceFromEntityAssembler {
    public static ReviewResource toResourceFromEntity(Review entity) {
        return new ReviewResource(
                entity.getId(),
                entity.getPatientId(),
                entity.getPsychologistId(),
                entity.getStars(),
                entity.getComment(),
                entity.isRead()
        );
    }
}
