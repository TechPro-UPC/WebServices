package com.techpro.upc.profiles_service.domain.model.aggregates;

import com.techpro.upc.profiles_service.domain.model.valueobjects.FullName;
import com.techpro.upc.profiles_service.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "psychologists")
@Getter
@NoArgsConstructor
public class Psychologist extends AuditableAbstractAggregateRoot<Psychologist> {

    // Los campos id, createdAt, updatedAt se heredan

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))
    })
    private FullName fullName;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(length = 20)
    private String phone;

    @Column(length = 10)
    private String gender;

    @Column(name = "license_number", unique = true, length = 10, nullable = false)
    private String licenseNumber;

    @Column(length = 100)
    private String specialization;

    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    public Psychologist(String firstName, String lastName, String dni,
                        String phone, String gender,
                        String licenseNumber, String specialization, Long userId) {

        // 🎯 Usamos el Value Object
        this.fullName = new FullName(firstName, lastName);

        this.dni = dni;
        this.phone = phone;
        this.gender = gender;
        this.licenseNumber = licenseNumber;
        this.specialization = specialization;
        this.userId = userId;
    }

    // Métodos de conveniencia del dominio
    public String getFirstName() { return fullName.getFirstName(); }
    public String getLastName() { return fullName.getLastName(); }
    public String getFullName() { return fullName.getFullName(); }
}
