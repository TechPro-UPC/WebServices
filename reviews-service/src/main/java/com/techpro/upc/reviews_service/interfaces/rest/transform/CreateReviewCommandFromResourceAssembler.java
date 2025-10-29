package com.techpro.upc.reviews_service.interfaces.rest.transform;

import com.techpro.upc.reviews_service.domain.model.commands.CreateReviewCommand;
import com.techpro.upc.reviews_service.interfaces.rest.resources.CreateReviewResource;

/**
 * Converts REST resources to domain commands for creating reviews.
 */
public class CreateReviewCommandFromResourceAssembler {
    public static CreateReviewCommand toCommandFromResource(CreateReviewResource resource) {
        return new CreateReviewCommand(
                resource.patientId(),
                resource.psychologistId(),
                resource.rating(),
                resource.comment(),
                false
        );
    }
}
