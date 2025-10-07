package com.techpro.upc.scheduling_service.domain.services;

import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import com.techpro.upc.scheduling_service.domain.model.commands.CreateReservationCommand;

import java.util.Optional;

public interface ReservationCommandService {
    Optional<Reservation> handle(CreateReservationCommand command);
}