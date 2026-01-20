package com.tp.microservices.organisateur.cqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganisateurCreatedEvent {
    
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private List evenementIds;
}