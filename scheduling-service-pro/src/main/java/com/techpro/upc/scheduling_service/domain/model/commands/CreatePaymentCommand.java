package com.techpro.upc.scheduling_service.domain.model.commands;

public record CreatePaymentCommand(float amount, String currency, boolean status) {
}
