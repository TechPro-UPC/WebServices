package com.techpro.upc.scheduling_service.application.internal.commandservices;

import com.techpro.upc.scheduling_service.domain.model.aggregates.TimeSlot;
import com.techpro.upc.scheduling_service.domain.model.commands.CreateTimeSlotCommand;
import com.techpro.upc.scheduling_service.domain.model.queries.GetAllTimeSlotsQuery;
import com.techpro.upc.scheduling_service.domain.model.queries.GetTimeSlotByIdQuery;
import com.techpro.upc.scheduling_service.domain.services.TimeSlotCommandService;
import com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeSlotCommandServiceImpl implements TimeSlotCommandService {

    private final TimeSlotRepository repository;

    public TimeSlotCommandServiceImpl(TimeSlotRepository repository) {
        this.repository = repository;
    }

    // ⇦ NO está en la interfaz; quita @Override
    public Optional<TimeSlot> handle(CreateTimeSlotCommand command) {
        var timeSlot = new TimeSlot(command);
        var saved = repository.save(timeSlot);
        return Optional.of(saved);
    }

    @Override
    public Optional<TimeSlot> handle(GetTimeSlotByIdQuery query) {
        // ajusta si tu record/cq tiene otro accessor
        return repository.findById(query.id());
    }

    @Override
    public List<TimeSlot> handle(GetAllTimeSlotsQuery query) {
        return repository.findAll();
    }
}