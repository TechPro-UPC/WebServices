package com.techpro.upc.scheduling_service.application.internal.commandservices;


import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import com.techpro.upc.scheduling_service.domain.model.commands.CreateReservationCommand;
import com.techpro.upc.scheduling_service.domain.services.ReservationCommandService;
import com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationCommandServiceImpl implements ReservationCommandService {

    private final ReservationRepository repository;

    public ReservationCommandServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Reservation> handle(CreateReservationCommand command) {
        if (command.patientId() == null || command.patientId() <= 0 ||
                command.psycologistId() == null || command.psycologistId() <= 0 ||
                command.paymentId() == null || command.paymentId() <= 0 ||
                command.timeSlotId() == null || command.timeSlotId()<=0 ||
                command.workerId() == null || command.workerId() <= 0) {
            throw new IllegalArgumentException("Invalid reservation command");
        }
        var reservation = new Reservation(command);
        repository.save(reservation);
        return Optional.of(reservation);
    }
}
