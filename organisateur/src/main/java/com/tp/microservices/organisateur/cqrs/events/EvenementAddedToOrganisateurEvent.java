package com.tp.microservices.organisateur.cqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EvenementAddedToOrganisateurEvent {
    
    private String id;
    private Integer evenementId;
}