package com.techpro.upc.scheduling_service.application.internal.queryservices;


import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import com.techpro.upc.scheduling_service.domain.model.queries.GetAllReservationsQuery;
import com.techpro.upc.scheduling_service.domain.model.queries.GetReservationByIdQuery;
import com.techpro.upc.scheduling_service.domain.services.ReservationQueryService;
import com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationQueryServiceImpl implements ReservationQueryService {
    private final ReservationRepository reservationRepository;

    public ReservationQueryServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> handle(GetAllReservationsQuery query) {
        return reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> handle(GetReservationByIdQuery query) {
        return reservationRepository.findById(query.id());
    }
}
