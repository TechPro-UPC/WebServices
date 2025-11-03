package com.techpro.upc.reviews_service.infrastructure.persistence.jpa.repositories;

import com.techpro.upc.reviews_service.domain.model.aggregates.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByPsychologistIdAndPatientId(Long psychologistId, Long patientId);
    List<Review> findByPsychologistId(Long psychologistId);
}
