package com.techpro.upc.profiles_service.infrastructure.messaging;

public record ProfileCreatedMessage(
        Long userId,
        String fullName,
        String email,
        String role
) {}