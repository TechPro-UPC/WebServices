package com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories;

import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
