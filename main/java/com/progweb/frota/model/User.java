package com.progweb.frota.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"user\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Role is mandatory") // 'ADMIN' ou 'MOTORISTA'
    private String role;

    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Document is mandatory")
    private String document;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6)
    @JsonIgnore
    private String password;
}