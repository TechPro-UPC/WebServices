package com.techpro.upc.catalog_service.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "psychologists")
public class Psychologist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long profileId;

    private Long userId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String gender;
    private String phone;
    private String cmp;

    // --- ESTOS SON LOS CAMPOS QUE TE FALTAN 👇 ---
    @Column(nullable = false)
    private Double rating = 0.0;

    @Column(nullable = false)
    private Integer reviewCount = 0;
    // ---------------------------------------------

    public Psychologist(Long profileId, Long userId, String firstName, String lastName, String specialization, String gender, String phone, String cmp) {
        this.profileId = profileId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.gender = gender;
        this.phone = phone;
        this.cmp = cmp;

        // Inicializamos también aquí
        this.rating = 0.0;
        this.reviewCount = 0;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
