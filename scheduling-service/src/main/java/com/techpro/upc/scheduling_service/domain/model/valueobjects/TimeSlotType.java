package com.techpro.upc.scheduling_service.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record TimeSlotType(String type) {
    public TimeSlotType{
        if(type == null || type.isBlank())
            throw new IllegalArgumentException("TimeSlotType cannot be null or empty");
        if(type.length() > 2)
            throw new IllegalArgumentException("TimeSlotType cannot have more than 2 characters");
    }
}
