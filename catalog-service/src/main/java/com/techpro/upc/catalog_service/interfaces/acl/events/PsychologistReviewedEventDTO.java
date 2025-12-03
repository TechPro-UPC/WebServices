package com.techpro.upc.catalog_service.interfaces.acl.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PsychologistReviewedEventDTO {
    private Long psychologistId; // Es el profileId en tu catálogo
    private int stars;
}
