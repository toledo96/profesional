package com.practice.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String usuario;

    @NotBlank
    @ToString.Exclude
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;
}

