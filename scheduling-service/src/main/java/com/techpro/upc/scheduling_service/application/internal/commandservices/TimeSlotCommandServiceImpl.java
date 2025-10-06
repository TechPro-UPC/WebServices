package com.techpro.upc.scheduling_service.application.internal.commandservices;


import com.techpro.upc.scheduling_service.domain.services.TimeSlotCommandService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimeSlotCommandServiceImpl implements TimeSlotCommandService {

    private final TimeSlotRepository repository;

    public TimeSlotCommandServiceImpl(TimeSlotRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<TimeSlot> handle(CreateTimeSlotCommand command) {
        var timeSlot = new TimeSlot(command);
        var saved = repository.save(timeSlot);
        return Optional.of(saved);
    }
}
