package com.techpro.upc.profiles_service.infrastructure.iam;

/**
 * Representa la estructura de respuesta que retorna el IAM Service
 * al consultar un usuario.
 */
public record UserResource(Long id, String email) {}