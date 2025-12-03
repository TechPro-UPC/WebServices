package com.techpro.upc.catalog_service.infrastructure.persistence.jpa.repositories;

import com.techpro.upc.catalog_service.domain.model.entities.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PsychologistRepository extends JpaRepository<Psychologist, Long> {
    Optional<Psychologist> findByProfileId(Long profileId);

    // Nuevo: Buscar por especialidad (ignorando mayúsculas/minúsculas)
    List<Psychologist> findBySpecializationContainingIgnoreCase(String specialization);
}
