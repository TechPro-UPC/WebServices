package com.techpro.upc.profiles_service.domain.model.aggregates;

import com.techpro.upc.profiles_service.domain.model.valueobjects.FullName;
import com.techpro.upc.profiles_service.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "patients")
// Heredamos las características de JPA y los campos de Auditoría de la clase base
@Getter
@NoArgsConstructor
public class Patient extends AuditableAbstractAggregateRoot<Patient> {

    // Los campos id, createdAt, updatedAt se heredan de AuditableAbstractAggregateRoot

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

    // Campo de referencia al usuario del microservicio IAM
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    // Constructor principal de dominio (sin el ID, ya que lo maneja la clase base)
    public Patient(String firstName, String lastName, String dni, String phone, String gender, Long userId) {
        this.fullName = new FullName(firstName, lastName);
        this.dni = dni;
        this.phone = phone;
        this.gender = gender;
        this.userId = userId;
    }

    // Métodos de conveniencia del dominio
    public String getFirstName() { return fullName.getFirstName(); }

    public String getLastName() { return fullName.getLastName(); }

    public String getFullName() { return fullName.getFullName(); }
}
