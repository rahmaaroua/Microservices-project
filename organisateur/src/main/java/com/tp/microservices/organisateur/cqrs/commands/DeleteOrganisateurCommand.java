package com.tp.microservices.organisateur.cqrs.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteOrganisateurCommand {
    
    @TargetAggregateIdentifier
    private String id;
}