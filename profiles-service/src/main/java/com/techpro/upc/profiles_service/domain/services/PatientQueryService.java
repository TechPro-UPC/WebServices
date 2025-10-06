package com.techpro.upc.profiles_service.domain.services;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.domain.model.queries.GetAllPatientsQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPatientByIdQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPatientByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface PatientQueryService {

    Optional<Patient> handle(GetPatientByIdQuery query);

    Optional<Patient> handle(GetPatientByUserIdQuery query);

    List<Patient> handle(GetAllPatientsQuery query);
}
