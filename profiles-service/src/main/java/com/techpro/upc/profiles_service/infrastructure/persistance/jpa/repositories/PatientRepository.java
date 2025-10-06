package com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories;

import com.techpro.upc.profiles_service.domain.model.aggregates.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByDni(String dni);

    Optional<Patient> findByUserId(Long userId);

    boolean existsByDni(String dni);
}