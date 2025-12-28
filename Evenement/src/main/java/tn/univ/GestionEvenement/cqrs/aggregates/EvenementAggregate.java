package tn.univ.GestionEvenement.cqrs.aggregates;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import tn.univ.GestionEvenement.cqrs.commands.CreateEvenementCommand;
import tn.univ.GestionEvenement.cqrs.events.EvenementCreatedEvent;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class EvenementAggregate {

    @AggregateIdentifier
    private String id;

    private String description;
    private Date dated;
    private Date datef;
    private float cout;

    // ========== COMMAND HANDLER ==========
    @CommandHandler
    public EvenementAggregate(CreateEvenementCommand command) {
        System.out.println("🔵 [AGGREGATE] Handling CreateEvenementCommand: " + command.getId());

        // Validation métier (optionnelle)
        if (command.getCout() < 0) {
            throw new IllegalArgumentException("Le coût ne peut pas être négatif");
        }

        // Publier l'événement
        AggregateLifecycle.apply(new EvenementCreatedEvent(
                command.getId(),
                command.getDescription(),
                command.getDated(),
                command.getDatef(),
                command.getCout()
        ));
    }

    // ========== EVENT SOURCING HANDLER ==========
    @EventSourcingHandler
    public void on(EvenementCreatedEvent event) {
        System.out.println("🔵 [AGGREGATE] Applying EvenementCreatedEvent: " + event.getId());

        this.id = event.getId();
        this.description = event.getDescription();
        this.dated = event.getDated();
        this.datef = event.getDatef();
        this.cout = event.getCout();
    }
}