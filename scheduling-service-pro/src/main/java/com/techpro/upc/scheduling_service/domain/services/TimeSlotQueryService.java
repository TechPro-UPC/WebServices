package com.techpro.upc.scheduling_service.domain.services;

import com.techpro.upc.scheduling_service.domain.model.aggregates.TimeSlot;
import com.techpro.upc.scheduling_service.domain.model.queries.GetAllTimeSlotsQuery;
import com.techpro.upc.scheduling_service.domain.model.queries.GetTimeSlotByIdQuery;

import java.util.List;
import java.util.Optional;

public interface TimeSlotQueryService {
    Optional<TimeSlot> handle(GetTimeSlotByIdQuery query);
    List<TimeSlot> handle(GetAllTimeSlotsQuery query);

}
