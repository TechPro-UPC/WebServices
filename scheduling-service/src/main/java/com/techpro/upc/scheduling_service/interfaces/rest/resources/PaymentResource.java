package com.techpro.upc.scheduling_service.interfaces.rest.resources;

public record PaymentResource(Long id, float amount, String currency, boolean status) {
}
