package com.techpro.upc.payments_service.infrastructure.persistance.jpa;

import com.techpro.upc.payments_service.domain.model.aggregates.PaymentSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentSourceRepository extends JpaRepository<PaymentSource, Long> {
    List<PaymentSource> findAll();
    Optional<PaymentSource> findById(Long id);
}
