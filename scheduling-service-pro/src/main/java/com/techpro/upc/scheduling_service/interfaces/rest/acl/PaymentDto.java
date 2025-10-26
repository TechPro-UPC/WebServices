package com.techpro.upc.scheduling_service.interfaces.rest.acl;

public record PaymentDto(Long id, float amount, String currency, boolean status) {
}
