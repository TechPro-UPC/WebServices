package com.techpro.upc.profiles_service.application.internal.queryservices;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import com.techpro.upc.profiles_service.domain.model.queries.GetAllPatientsQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPatientByIdQuery;
import com.techpro.upc.profiles_service.domain.model.queries.GetPatientByUserIdQuery;
import com.techpro.upc.profiles_service.domain.services.PatientQueryService;
import com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientQueryServiceImpl implements PatientQueryService {

    private final PatientRepository patientRepository;

    public PatientQueryServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Optional<Patient> handle(GetPatientByIdQuery query) {
        return patientRepository.findById(query.patientId());
    }

    @Override
    public Optional<Patient> handle(GetPatientByUserIdQuery query) {
        return patientRepository.findByUserId(query.userId());
    }

    @Override
    public List<Patient> handle(GetAllPatientsQuery query) {
        return patientRepository.findAll();
    }
}
