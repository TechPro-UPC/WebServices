package com.techpro.upc.reviews_service.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PsychologistReviewedEvent extends ApplicationEvent {
    private final Long psychologistId;
    private final int stars;

    public PsychologistReviewedEvent(Object source, Long psychologistId, int stars) {
        super(source);
        this.psychologistId = psychologistId;
        this.stars = stars;
    }
}
