package com.tp.microservices.organisateur.cqrs.aggregates;

import com.tp.microservices.organisateur.cqrs.commands.*;
import com.tp.microservices.organisateur.cqrs.events.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Aggregate
public class OrganisateurAggregate {

    private static final Logger logger = LoggerFactory.getLogger(OrganisateurAggregate.class);

    @AggregateIdentifier
    private String id;
    
    private String nom;
    private String prenom;
    private String email;
    private List evenementIds;

    // Constructeur sans argument requis par Axon
    public OrganisateurAggregate() {
    }

    // ========== COMMAND HANDLERS ==========

    @CommandHandler
    public OrganisateurAggregate(CreateOrganisateurCommand command) {
        logger.info("🔵 [AGGREGATE] Handling CreateOrganisateurCommand for ID: {}", command.getId());
        
        // Validation métier
        if (command.getNom() == null || command.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide");
        }
        if (command.getPrenom() == null || command.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide");
        }
        if (command.getEmail() == null || !command.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email invalide");
        }

        // Publier l'événement
        AggregateLifecycle.apply(new OrganisateurCreatedEvent(
                command.getId(),
                command.getNom(),
                command.getPrenom(),
                command.getEmail(),
                command.getEvenementIds() != null ? command.getEvenementIds() : new ArrayList<>()
        ));
        
        logger.info("✅ [AGGREGATE] OrganisateurCreatedEvent published for ID: {}", command.getId());
    }

    @CommandHandler
    public void handle(UpdateOrganisateurCommand command) {
        logger.info("🔵 [AGGREGATE] Handling UpdateOrganisateurCommand for ID: {}", command.getId());
        
        // Validation métier
        if (command.getNom() == null || command.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide");
        }
        if (command.getPrenom() == null || command.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide");
        }
        if (command.getEmail() == null || !command.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email invalide");
        }

        // Publier l'événement
        AggregateLifecycle.apply(new OrganisateurUpdatedEvent(
                command.getId(),
                command.getNom(),
                command.getPrenom(),
                command.getEmail(),
                command.getEvenementIds() != null ? command.getEvenementIds() : this.evenementIds
        ));
        
        logger.info("✅ [AGGREGATE] OrganisateurUpdatedEvent published for ID: {}", command.getId());
    }

    @CommandHandler
    public void handle(DeleteOrganisateurCommand command) {
        logger.info("🔵 [AGGREGATE] Handling DeleteOrganisateurCommand for ID: {}", command.getId());
        
        // Publier l'événement
        AggregateLifecycle.apply(new OrganisateurDeletedEvent(command.getId()));
        
        logger.info("✅ [AGGREGATE] OrganisateurDeletedEvent published for ID: {}", command.getId());
    }

    @CommandHandler
    public void handle(AddEvenementToOrganisateurCommand command) {
        logger.info("🔵 [AGGREGATE] Handling AddEvenementToOrganisateurCommand for ID: {}", command.getId());
        
        // Validation
        if (this.evenementIds.contains(command.getEvenementId())) {
            logger.warn("⚠️ [AGGREGATE] Evenement {} déjà associé à l'organisateur {}", 
                       command.getEvenementId(), command.getId());
            return; // Pas d'événement si déjà existant
        }

        // Publier l'événement
        AggregateLifecycle.apply(new EvenementAddedToOrganisateurEvent(
                command.getId(),
                command.getEvenementId()
        ));
        
        logger.info("✅ [AGGREGATE] EvenementAddedToOrganisateurEvent published for ID: {}", command.getId());
    }

    // ========== EVENT SOURCING HANDLERS ==========

    @EventSourcingHandler
    public void on(OrganisateurCreatedEvent event) {
        logger.info("📝 [EVENT SOURCING] Applying OrganisateurCreatedEvent for ID: {}", event.getId());
        
        this.id = event.getId();
        this.nom = event.getNom();
        this.prenom = event.getPrenom();
        this.email = event.getEmail();
        this.evenementIds = event.getEvenementIds() != null ? 
                           new ArrayList<>(event.getEvenementIds()) : new ArrayList<>();
    }

    @EventSourcingHandler
    public void on(OrganisateurUpdatedEvent event) {
        logger.info("📝 [EVENT SOURCING] Applying OrganisateurUpdatedEvent for ID: {}", event.getId());
        
        this.nom = event.getNom();
        this.prenom = event.getPrenom();
        this.email = event.getEmail();
        this.evenementIds = event.getEvenementIds() != null ? 
                           new ArrayList<>(event.getEvenementIds()) : this.evenementIds;
    }

    @EventSourcingHandler
    public void on(OrganisateurDeletedEvent event) {
        logger.info("📝 [EVENT SOURCING] Applying OrganisateurDeletedEvent for ID: {}", event.getId());
        // L'agrégat est marqué comme supprimé
        AggregateLifecycle.markDeleted();
    }

    @EventSourcingHandler
    public void on(EvenementAddedToOrganisateurEvent event) {
        logger.info("📝 [EVENT SOURCING] Applying EvenementAddedToOrganisateurEvent for ID: {}", event.getId());
        
        if (!this.evenementIds.contains(event.getEvenementId())) {
            this.evenementIds.add(event.getEvenementId());
        }
    }
}