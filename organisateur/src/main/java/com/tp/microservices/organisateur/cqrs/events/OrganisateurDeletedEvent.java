package com.tp.microservices.organisateur.cqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganisateurDeletedEvent {
    
    private String id;
}