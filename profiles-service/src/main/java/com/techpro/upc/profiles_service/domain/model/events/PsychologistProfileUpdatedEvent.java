package com.techpro.upc.profiles_service.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PsychologistProfileUpdatedEvent extends ApplicationEvent {
    private final Long psychologistId;
    private final Long userId; // Útil para vincular
    private final String firstName;
    private final String lastName;
    private final String specialization;
    private final String gender;
    private final String phone;
    private final String cmp; // License Number

    public PsychologistProfileUpdatedEvent(Object source, Long psychologistId, Long userId, String firstName, String lastName, String specialization, String gender, String phone, String cmp) {
        super(source);
        this.psychologistId = psychologistId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.gender = gender;
        this.phone = phone;
        this.cmp = cmp;
    }
}
