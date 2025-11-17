package com.techpro.upc.profiles_service.application.acl;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PsychologistRepository;
import com.techpro.upc.profiles_service.interfaces.acl.PsychologistContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PsychologistContextFacadeImpl implements PsychologistContextFacade {

    private final PsychologistRepository psychologistRepository;

    public PsychologistContextFacadeImpl(PsychologistRepository psychologistRepository) {
        this.psychologistRepository = psychologistRepository;
    }

    @Override
    public Optional<Psychologist> fetchPsychologistById(Long id) {
        return psychologistRepository.findById(id);
    }

    @Override
    public Optional<Psychologist> fetchPsychologistByUserId(Long userId) {
        return psychologistRepository.findByUserId(userId);
    }
}
