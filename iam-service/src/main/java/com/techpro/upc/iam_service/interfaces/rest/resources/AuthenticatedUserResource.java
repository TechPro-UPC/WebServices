package com.techpro.upc.iam_service.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String email, String token) {
}