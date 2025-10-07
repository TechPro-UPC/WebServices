package com.techpro.upc.profiles_service.application.internal.queryservices;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import com.techpro.upc.profiles_service.domain.model.queries.GetAllPsychologistsQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPsychologistByIdQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPsychologistByUserIdQuery;
import com.techpro.upc.profiles_service.domain.services.PsychologistQueryService;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PsychologistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PsychologistQueryServiceImpl implements PsychologistQueryService {

    private final PsychologistRepository psychologistRepository;

    public PsychologistQueryServiceImpl(PsychologistRepository psychologistRepository) {
        this.psychologistRepository = psychologistRepository;
    }

    @Override
    public List<Psychologist> handle(GetAllPsychologistsQuery query) {
        return psychologistRepository.findAll();
    }

    @Override
    public Optional<Psychologist> handle(GetPsychologistByIdQuery query) {
        return psychologistRepository.findById(query.psychologistId());
    }

    @Override
    public Optional<Psychologist> handle(GetPsychologistByUserIdQuery query) {
        return psychologistRepository.findByUserId(query.userId());
    }
}
