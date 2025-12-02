package com.techpro.upc.scheduling_service.application.internal.commandservices;

import com.techpro.upc.scheduling_service.application.internal.outboundservices.profiles.PatientContextFacade;
import com.techpro.upc.scheduling_service.application.internal.outboundservices.profiles.PsychologistContextFacade;
import com.techpro.upc.scheduling_service.domain.model.aggregates.Reservation;
import com.techpro.upc.scheduling_service.domain.model.commands.CreateReservationCommand;
import com.techpro.upc.scheduling_service.domain.services.ReservationCommandService;
import com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories.ReservationRepository;
import com.techpro.upc.scheduling_service.infrastructure.persistence.jpa.repositories.TimeSlotRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationCommandServiceImpl implements ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final TimeSlotRepository timeSlotRepository; // Para validar que el horario exista
    private final PsychologistContextFacade psychologistContextFacade;
    private final PatientContextFacade patientContextFacade; // Para validar al paciente

    public ReservationCommandServiceImpl(ReservationRepository reservationRepository,
                                         TimeSlotRepository timeSlotRepository,
                                         PsychologistContextFacade psychologistContextFacade,
                                         PatientContextFacade patientContextFacade) {
        this.reservationRepository = reservationRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.psychologistContextFacade = psychologistContextFacade;
        this.patientContextFacade = patientContextFacade;
    }

    @Override
    @Transactional
    public Optional<Reservation> handle(CreateReservationCommand command) {

        // 1. Validar integridad de datos básicos
        if (command.patientId() == null || command.psychologistId() == null || command.timeSlotId() == null) {
            throw new IllegalArgumentException("Reserva inválida: Faltan IDs necesarios");
        }

        // 2. Validar que el TimeSlot exista y esté libre (Lógica interna del microservicio)
        var timeSlot = timeSlotRepository.findById(command.timeSlotId())
                .orElseThrow(() -> new IllegalArgumentException("El horario (TimeSlot) no existe"));

        if (timeSlot.isReserved()) { // Asumiendo que TimeSlot tiene un campo o lógica de estado
            throw new IllegalArgumentException("El horario seleccionado ya está reservado");
        }

        // 3. Validar existencia externa (Comunicación entre microservicios)
        // Validar Psicólogo
        psychologistContextFacade.fetchPsychologistById(command.psychologistId());

        // Validar Paciente (NUEVO)
        patientContextFacade.fetchPatientById(command.patientId());

        // 4. Crear Reserva
        var reservation = new Reservation(command);
        reservationRepository.save(reservation);

        // 5. Actualizar el TimeSlot a "Ocupado"
        timeSlot.markAsReserved(); // Método que debes tener en tu entidad TimeSlot
        timeSlotRepository.save(timeSlot);

        return Optional.of(reservation);
    }
}
