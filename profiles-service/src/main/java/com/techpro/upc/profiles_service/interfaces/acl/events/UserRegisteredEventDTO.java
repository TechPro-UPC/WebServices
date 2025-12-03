package com.techpro.upc.profiles_service.interfaces.acl.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Este DTO actúa como un "espejo" del evento que emite el IAM.
 * Sirve para deserializar el mensaje JSON que llega desde RabbitMQ.
 */
@Getter
@Setter
@NoArgsConstructor // Vital para que Jackson pueda crear la instancia vacía
public class UserRegisteredEventDTO {

    private Long userId;
    private String email;

    // Recibimos el rol como String ("PATIENT", "PSYCHOLOGIST")
    // para evitar depender del Enum del otro microservicio.
    private String role;

    // Constructor de conveniencia (opcional, útil para tests)
    public UserRegisteredEventDTO(Long userId, String email, String role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
}
