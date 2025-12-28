package tn.univ.GestionEvenement.cqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EvenementCreatedEvent {

    private String id;
    private String description;
    private Date dated;
    private Date datef;
    private float cout;
}