package com.techpro.upc.iam_service.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AbstractAggregateRoot<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {}

    public User(String email, String password, Role rol) {
        this.email = email;
        this.password = password;
        this.role = rol;
    }


}
