package com.techpro.upc.profiles_service.infrastructure.persistance.jpa.repositories;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PsychologistRepository extends JpaRepository<Psychologist, Long> {
    Optional<Psychologist> findByUserId(Long userId);

    // Validar si existe otro psicólogo con este DNI (excluyendo mi propio ID)
    boolean existsByDniAndIdNot(String dni, Long id);

    // Validar si existe otro con esta licencia (excluyendo mi propio ID)
    boolean existsByLicenseNumberAndIdNot(String licenseNumber, Long id);
}
