package com.techpro.upc.iam_service.domain.model.commands;



public record SignInCommand(String email, String password) {
}