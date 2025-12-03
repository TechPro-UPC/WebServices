package com.techpro.upc.iam_service.application.internal.commandservices;


import com.techpro.upc.iam_service.application.internal.outboundservices.hashing.HashingService;
import com.techpro.upc.iam_service.application.internal.outboundservices.tokens.TokenService;
import com.techpro.upc.iam_service.domain.model.aggregates.Role;
import com.techpro.upc.iam_service.domain.model.aggregates.User;
import com.techpro.upc.iam_service.domain.model.commands.SignInCommand;
import com.techpro.upc.iam_service.domain.model.commands.SignUpCommand;
import com.techpro.upc.iam_service.domain.model.events.UserRegisteredEvent;
import com.techpro.upc.iam_service.domain.services.UserCommandService;
import com.techpro.upc.iam_service.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher; // 1. Inyectamos el publicador

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
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

        if (command.role() == Role.ADMIN) {
            throw new RuntimeException("Cannot register as Admin");
        }

        var user = new User(command.email(), hashingService.encode(command.password()), command.role());

        // 2. Guardamos PRIMERO (Aquí MySQL genera el ID)
        userRepository.save(user);

        // 3. Ahora que user.getId() ya no es null, publicamos el evento
        var userRegisteredEvent = new UserRegisteredEvent(this, user.getId(), user.getEmail(), user.getRole());
        eventPublisher.publishEvent(userRegisteredEvent);

        return userRepository.findByEmail(command.email());
    }
}
