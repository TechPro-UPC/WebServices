package com.techpro.upc.reviews_service.application.internal.queryservices;

import com.techpro.upc.reviews_service.domain.model.aggregates.Review;
import com.techpro.upc.reviews_service.domain.model.queries.GetAllReviewsQuery;
import com.techpro.upc.reviews_service.domain.model.queries.GetReviewByIdQuery;
import com.techpro.upc.reviews_service.domain.model.queries.GetReviewByPsychologistIdAndPatientIdQuery;
import com.techpro.upc.reviews_service.domain.model.queries.GetReviewsByPsychologistIdQuery;
import com.techpro.upc.reviews_service.domain.model.services.ReviewQueryService;
import com.techpro.upc.reviews_service.infrastructure.persistence.jpa.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ReviewQueryService for handling read-side queries.
 */
@Service
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public ReviewQueryServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Optional<Review> handle(GetReviewByPsychologistIdAndPatientIdQuery query) {
        return reviewRepository.findByPsychologistIdAndPatientId(
                query.psychologistId(), query.patientId()
        );
    }

    @Override
    public Optional<Review> handle(GetReviewByIdQuery query) {
        return reviewRepository.findById(query.id());
    }

    @Override
    public List<Review> handle(GetAllReviewsQuery query) {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> handle(GetReviewsByPsychologistIdQuery query) {
        return reviewRepository.findByPsychologistId(query.psychologistId());
    }
}
