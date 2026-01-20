package com.tp.microservices.organisateur.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organisateurs")
public class Organisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrg;

    // UUID pour lier avec l'Aggregate Axon
    @Column(unique = true, nullable = false)
    private String aggregateId;
    
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    // Store IDs of events this organizer is responsible for
    @ElementCollection
    private List<Integer> evenementIds = new ArrayList<>();
}
