package com.techpro.upc.profiles_service.domain.model.aggregates;

import com.techpro.upc.profiles_service.domain.model.valueobjects.FullName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "patients")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    // Constructor principal de dominio
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
