package com.techpro.upc.iam_service.domain.model.commands;


import com.techpro.upc.iam_service.domain.model.aggregates.Role;

public record SignUpCommand(String email, String password, Role role) {
}
