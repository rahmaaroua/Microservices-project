package com.tp.microservices.organisateur.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganisateurDTO {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private List evenementIds;
}