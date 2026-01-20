package com.tp.microservices.organisateur.cqrs.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganisateurCommand {
    
    @TargetAggregateIdentifier
    private String id; // UUID pour l'aggregate
    
    private String nom;
    private String prenom;
    private String email;
    private List evenementIds;
}