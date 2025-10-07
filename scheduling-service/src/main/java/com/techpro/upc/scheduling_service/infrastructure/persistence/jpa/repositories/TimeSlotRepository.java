package com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories;

import com.techpro.upc.scheduling_service.domain.model.aggregates.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}
