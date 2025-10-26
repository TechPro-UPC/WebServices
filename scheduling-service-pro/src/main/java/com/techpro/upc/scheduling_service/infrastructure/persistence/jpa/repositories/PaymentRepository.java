package com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories;

import com.techpro.upc.scheduling_service.domain.model.aggregates.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
