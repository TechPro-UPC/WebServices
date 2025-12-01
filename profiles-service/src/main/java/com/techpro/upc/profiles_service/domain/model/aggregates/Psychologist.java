package com.techpro.upc.profiles_service.domain.model.aggregates;

import com.techpro.upc.profiles_service.domain.model.valueobjects.FullName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "psychologists")
@Getter
@Setter
@NoArgsConstructor
public class Psychologist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ELIMINADO: @NotBlank (El nombre no existe al momento del registro inicial)
    @Size(max = 50)
    @Column(name = "first_name")
    private String firstName;

    // ELIMINADO: @NotBlank
    @Size(max = 50)
    @Column(name = "last_name")
    private String lastName;

    // ELIMINADO: @NotBlank
    @Size(min = 8, max = 8)
    private String dni;

    @Size(max = 15)
    private String phone;

    private String gender;

    // ELIMINADO: @NotBlank
    // Aún es único, pero permite null temporalmente hasta que el psicólogo suba su licencia
    @Size(min = 6, max = 10)
    @Column(name = "license_number", unique = true, length = 10)
    private String licenseNumber;

    @Size(max = 100)
    private String specialization;

    // Este campo SÍ es obligatorio, es el vínculo con el IAM
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    // --- NUEVO: Constructor Mínimo para RabbitMQ ---
    // Este es el que usa el Listener cuando llega el evento "UserRegistered"
    public Psychologist(Long userId) {
        this.userId = userId;
    }

    // Constructor completo (Útil para Tests o Seeds)
    public Psychologist(String firstName, String lastName, String dni,
                        String phone, String gender,
                        String licenseNumber, String specialization, Long userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;
        this.gender = gender;
        this.licenseNumber = licenseNumber;
        this.specialization = specialization;
        this.userId = userId;
    }

    // --- NUEVO: Metodo de Negocio ---
    // Usa este metodo cuando el psicólogo entre a "Editar Perfil" para llenar sus datos
    public void updateProfile(String firstName, String lastName, String dni, String phone, String gender, String licenseNumber, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;
        this.gender = gender;
        this.licenseNumber = licenseNumber;
        this.specialization = specialization;
    }
}
