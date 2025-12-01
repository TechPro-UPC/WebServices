package com.techpro.upc.iam_service.application.internal.commandservices;


import com.techpro.upc.iam_service.application.internal.outboundservices.hashing.HashingService;
import com.techpro.upc.iam_service.application.internal.outboundservices.tokens.TokenService;

import com.techpro.upc.iam_service.domain.model.aggregates.User;
import com.techpro.upc.iam_service.domain.model.commands.SignInCommand;
import com.techpro.upc.iam_service.domain.model.commands.SignUpCommand;
import com.techpro.upc.iam_service.domain.services.UserCommandService;
import com.techpro.upc.iam_service.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;


    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }


    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var token = tokenService.generateToken(user.get().getEmail());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }


    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(command.email()))
            throw new RuntimeException("Username already exists");
        var user = new User(command.email(), hashingService.encode(command.password()), command.role());
        userRepository.save(user);
        return userRepository.findByEmail(command.email());
    }
}