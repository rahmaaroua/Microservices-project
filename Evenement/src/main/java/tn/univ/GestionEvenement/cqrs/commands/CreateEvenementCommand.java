package tn.univ.GestionEvenement.cqrs.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEvenementCommand {

    @TargetAggregateIdentifier
    private String id;

    private String description;
    private Date dated;
    private Date datef;
    private float cout;
}