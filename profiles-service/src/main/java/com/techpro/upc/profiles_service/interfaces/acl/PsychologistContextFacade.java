package com.techpro.upc.profiles_service.interfaces.acl;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;

import java.util.Optional;

public interface PsychologistContextFacade {
    Optional<Psychologist> fetchPsychologistById(Long id);
    Optional<Psychologist> fetchPsychologistByUserId(Long userId);
}
