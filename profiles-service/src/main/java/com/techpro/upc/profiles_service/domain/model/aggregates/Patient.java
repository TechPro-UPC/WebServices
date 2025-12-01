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
            // CAMBIO 1: Permitimos nulos temporalmente (nullable = true)
            // porque al registrarse en IAM no tenemos el nombre aún.
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name", nullable = true)),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name", nullable = true))
    })
    private FullName fullName;

    // CAMBIO 2: Eliminamos 'nullable = false'
    // El DNI se llenará en un segundo paso (Actualizar Perfil).
    @Column(unique = true, length = 8)
    private String dni;

    @Column(length = 20)
    private String phone;

    @Column(length = 10)
    private String gender;

    // Este sí es obligatorio porque viene del IAM
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    // CAMBIO 3: Constructor para RabbitMQ (Creación automática)
    public Patient(Long userId) {
        this.userId = userId;
        // El resto queda en null hasta que el usuario complete su perfil
    }

    // Constructor completo (por si lo necesitas para tests o seeders)
    public Patient(String firstName, String lastName, String dni, String phone, String gender, Long userId) {
        this.fullName = new FullName(firstName, lastName);
        this.dni = dni;
        this.phone = phone;
        this.gender = gender;
        this.userId = userId;
    }

    // CAMBIO 4: Metodo de Negocio para completar el perfil
    // Usa este metodo en tu PatientCommandServiceImpl cuando llegue el comando UpdatePatientProfile
    public void updateProfile(String firstName, String lastName, String dni, String phone, String gender) {
        this.fullName = new FullName(firstName, lastName);
        this.dni = dni;
        this.phone = phone;
        this.gender = gender;
    }

    // Métodos de conveniencia (con manejo de nulos para evitar NullPointerException)
    public String getFirstName() {
        return fullName != null ? fullName.getFirstName() : null;
    }

    public String getLastName() {
        return fullName != null ? fullName.getLastName() : null;
    }

    public String getFullName() {
        return fullName != null ? fullName.getFullName() : null;
    }
}
