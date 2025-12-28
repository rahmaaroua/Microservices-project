package tn.univ.GestionEvenement.cqrs.queries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GetEvenementByIdQuery {

    private final String id;

    @JsonCreator  // ← OBLIGATOIRE pour Axon
    public GetEvenementByIdQuery(@JsonProperty("id") String id) {
        this.id = id;
    }
}