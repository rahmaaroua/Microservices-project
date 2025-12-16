package tn.univ.ReservationMicroservice.cqrs.aggregates;

import tn.univ.ReservationMicroservice.cqrs.commands.CreateReservationCQRSCommand;
import tn.univ.ReservationMicroservice.cqrs.events.ReservationCQRSCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import java.util.Date;

@Aggregate
public class ReservationCQRSAggregate {

    @AggregateIdentifier
    private String idReservation;
    private Date dateReservation;
    private boolean confirme;

    public ReservationCQRSAggregate() {
    }

    @CommandHandler
    public ReservationCQRSAggregate(CreateReservationCQRSCommand command) {

        // Validation
        if (command.getDateReservation().before(new Date())) {
            throw new IllegalArgumentException("La date ne peut pas être dans le passé");
        }

        // Publier l'événement
        AggregateLifecycle.apply(new ReservationCQRSCreatedEvent(
                command.getIdReservation(),
                command.getDateReservation(),
                command.isConfirme()
        ));
    }

    @EventSourcingHandler
    public void on(ReservationCQRSCreatedEvent event) {
        this.idReservation = event.getIdReservation();
        this.dateReservation = event.getDateReservation();
        this.confirme = event.isConfirme();
    }
}