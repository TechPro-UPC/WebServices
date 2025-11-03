package com.techpro.upc.reviews_service.integration;

import com.techpro.upc.reviews_service.application.internal.queryservices.ReviewQueryServiceImpl;
import com.techpro.upc.reviews_service.domain.model.aggregates.Review;
import com.techpro.upc.reviews_service.domain.model.queries.GetAllReviewsQuery;
import com.techpro.upc.reviews_service.infrastructure.persistence.jpa.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(ReviewQueryServiceImpl.class)
@ActiveProfiles("test")
class ReviewQueryServiceIntegrationTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewQueryServiceImpl reviewQueryService;

    @BeforeEach
    void setUp() {
        Review r1 = new Review();
        r1.setPatientId(1L);
        r1.setPsychologistId(10L);
        r1.setComment("Excelente atención");
        r1.setRating(5);

        Review r2 = new Review();
        r2.setPatientId(2L);
        r2.setPsychologistId(20L);
        r2.setComment("Muy buena experiencia");
        r2.setRating(4);

        reviewRepository.save(r1);
        reviewRepository.save(r2);
    }

    @Test
    void testHandle_GetAllReviewsQuery_ReturnsAll() {
        List<Review> result = reviewQueryService.handle(new GetAllReviewsQuery());

        assertEquals(2, result.size());
    }
}
