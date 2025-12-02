package com.techpro.upc.scheduling_service.application.internal.commandservices;

import com.techpro.upc.scheduling_service.application.internal.outboundservices.profiles.PsychologistContextFacade;
import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import com.techpro.upc.scheduling_service.domain.model.commands.CreateReservationCommand;
import com.techpro.upc.scheduling_service.domain.services.ReservationCommandService;
import com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationCommandServiceImpl implements ReservationCommandService {

    private final ReservationRepository repository;
    private final PsychologistContextFacade psychologistContextFacade;

    public ReservationCommandServiceImpl(ReservationRepository repository,
                                         PsychologistContextFacade psychologistContextFacade) {
        this.repository = repository;
        this.psychologistContextFacade = psychologistContextFacade;
    }

    @Override
    public Optional<Reservation> handle(CreateReservationCommand command) {
        if (command.patientId() == null || command.patientId() <= 0 ||
                command.psycologistId() == null || command.psycologistId() <= 0 ||
                command.timeSlotId() == null || command.timeSlotId() <= 0 ) {
            throw new IllegalArgumentException("Invalid reservation command");
        }

        // 🧩 Validar que el psicólogo exista en Profiles Service
        psychologistContextFacade.fetchPsychologistById(command.psycologistId());

        var reservation = new Reservation(command);
        repository.save(reservation);
        return Optional.of(reservation);
    }
}
