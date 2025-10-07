package com.techpro.upc.profiles_service.domain.services;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import com.techpro.upc.profiles_service.domain.model.queries.GetAllPsychologistsQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPsychologistByIdQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPsychologistByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface PsychologistQueryService {
    List<Psychologist> handle(GetAllPsychologistsQuery query);
    Optional<Psychologist> handle(GetPsychologistByIdQuery query);
    Optional<Psychologist> handle(GetPsychologistByUserIdQuery query);
}
