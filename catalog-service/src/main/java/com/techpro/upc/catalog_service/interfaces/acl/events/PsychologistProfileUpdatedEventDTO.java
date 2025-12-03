package com.techpro.upc.catalog_service.interfaces.acl.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Vital para que Jackson pueda deserializar el JSON vacío
@AllArgsConstructor
public class PsychologistProfileUpdatedEventDTO {

    private Long psychologistId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String gender;
    private String phone;
    private String cmp; // Debe llamarse igual que en el evento del emisor (Profiles)
}
