package com.techpro.upc.iam_service.infrastructure.persistence.jpa.repositories;

import com.techpro.upc.iam_service.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}