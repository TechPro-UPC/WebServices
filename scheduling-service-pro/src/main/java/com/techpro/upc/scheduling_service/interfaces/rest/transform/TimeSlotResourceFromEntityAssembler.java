package com.techpro.upc.scheduling_service.interfaces.rest.transform;


import com.techpro.upc.scheduling_service.domain.model.aggregates.TimeSlot;
import com.techpro.upc.scheduling_service.interfaces.rest.resources.TimeSlotResource;

public class TimeSlotResourceFromEntityAssembler {
    public static TimeSlotResource toResourceFromEntity(TimeSlot entity) {
        return new TimeSlotResource(
                entity.getId(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }
}
