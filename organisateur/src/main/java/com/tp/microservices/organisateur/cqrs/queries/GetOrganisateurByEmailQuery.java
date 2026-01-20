package com.tp.microservices.organisateur.cqrs.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganisateurByEmailQuery {
    private String email;
}