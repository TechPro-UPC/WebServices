package com.techpro.upc.reviews_service.domain.model.services;

import com.techpro.upc.reviews_service.domain.model.aggregates.Review;
import com.techpro.upc.reviews_service.domain.model.queries.GetAllReviewsQuery;
import com.techpro.upc.reviews_service.domain.model.queries.GetReviewByIdQuery;
import com.techpro.upc.reviews_service.domain.model.queries.GetReviewByPsychologistIdAndPatientIdQuery;
import com.techpro.upc.reviews_service.domain.model.queries.GetReviewsByPsychologistIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service for handling read operations on the Review aggregate.
 */
public interface ReviewQueryService {
    Optional<Review> handle(GetReviewByIdQuery query);
    List<Review> handle(GetAllReviewsQuery query);
    Optional<Review> handle(GetReviewByPsychologistIdAndPatientIdQuery query);
    List<Review> handle(GetReviewsByPsychologistIdQuery query);
}
