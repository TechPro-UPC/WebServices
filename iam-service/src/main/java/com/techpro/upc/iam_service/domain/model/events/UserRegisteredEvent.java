package com.techpro.upc.iam_service.domain.model.events;

import com.techpro.upc.iam_service.domain.model.aggregates.Role;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegisteredEvent extends ApplicationEvent {
    private final Long userId;
    private final String email;
    private final Role role;

    public UserRegisteredEvent(Object source, Long userId, String email, Role role) {
        super(source);
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
}
