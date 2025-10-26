package com.techpro.upc.scheduling_service.application.internal.queryservices;


import com.techpro.upc.scheduling_service.domain.model.aggregates.TimeSlot;
import com.techpro.upc.scheduling_service.domain.model.queries.GetAllTimeSlotsQuery;
import com.techpro.upc.scheduling_service.domain.model.queries.GetTimeSlotByIdQuery;
import com.techpro.upc.scheduling_service.domain.services.TimeSlotQueryService;
import com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeSlotQueryServiceImpl implements TimeSlotQueryService {
    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotQueryServiceImpl(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    public Optional<TimeSlot> handle(GetTimeSlotByIdQuery query) {
        return timeSlotRepository.findById(query.id());
    }

    public List<TimeSlot> handle(GetAllTimeSlotsQuery query){
        return timeSlotRepository.findAll();
    }
}
