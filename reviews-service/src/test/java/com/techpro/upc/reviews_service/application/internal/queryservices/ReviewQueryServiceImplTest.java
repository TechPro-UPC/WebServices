package com.techpro.upc.reviews_service.application.internal.queryservices;

import com.techpro.upc.reviews_service.domain.model.aggregates.Review;
import com.techpro.upc.reviews_service.domain.model.queries.GetAllReviewsQuery;
import com.techpro.upc.reviews_service.domain.model.queries.GetReviewByIdQuery;
import com.techpro.upc.reviews_service.domain.model.queries.GetReviewByPsychologistIdAndPatientIdQuery;
import com.techpro.upc.reviews_service.domain.model.queries.GetReviewsByPsychologistIdQuery;
import com.techpro.upc.reviews_service.infrastructure.persistence.jpa.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class ReviewQueryServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewQueryServiceImpl reviewQueryService;

    private Review review1;
    private Review review2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        review1 = new Review();
        review2 = new Review();
    }

    @Test
    void testHandle_GetAllReviewsQuery() {
        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review1, review2));

        List<Review> reviews = reviewQueryService.handle(new GetAllReviewsQuery());

        assertEquals(2, reviews.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testHandle_GetReviewByIdQuery() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review1));

        Optional<Review> result = reviewQueryService.handle(new GetReviewByIdQuery(1L));

        assertTrue(result.isPresent());
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    void testHandle_GetReviewByPsychologistIdAndPatientIdQuery() {
        when(reviewRepository.findByPsychologistIdAndPatientId(1L, 2L)).thenReturn(Optional.of(review1));

        Optional<Review> result = reviewQueryService.handle(
                new GetReviewByPsychologistIdAndPatientIdQuery(1L, 2L));

        assertTrue(result.isPresent());
        verify(reviewRepository, times(1))
                .findByPsychologistIdAndPatientId(1L, 2L);
    }

    @Test
    void testHandle_GetReviewsByPsychologistIdQuery() {
        when(reviewRepository.findByPsychologistId(1L)).thenReturn(List.of(review1));

        List<Review> result = reviewQueryService.handle(new GetReviewsByPsychologistIdQuery(1L));

        assertEquals(1, result.size());
        verify(reviewRepository, times(1)).findByPsychologistId(1L);
    }
}
