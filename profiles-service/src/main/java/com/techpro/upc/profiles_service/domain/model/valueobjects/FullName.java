package com.techpro.upc.profiles_service.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record FullName(String firstName, String lastName) {

    // Constructor sin argumentos (útil para algunos frameworks y deserialización)
    public FullName() {
        this(null, null);
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getFullName() {
        if (firstName == null && lastName == null) return "";
        if (firstName == null) return lastName;
        if (lastName == null) return firstName;
        return firstName + " " + lastName;
    }
}
