package com.techpro.upc.profiles_service.domain.services;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import com.techpro.upc.profiles_service.domain.model.commands.CreatePsychologistCommand;

import java.util.Optional;

public interface PsychologistCommandService {
    Optional<Psychologist> handle(CreatePsychologistCommand command);
}
