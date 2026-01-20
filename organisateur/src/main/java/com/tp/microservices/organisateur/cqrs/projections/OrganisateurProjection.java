package com.tp.microservices.organisateur.cqrs.projections;

import com.tp.microservices.organisateur.cqrs.events.*;
import com.tp.microservices.organisateur.cqrs.queries.*;
import com.tp.microservices.organisateur.dto.OrganisateurDTO;
import com.tp.microservices.organisateur.persistence.entities.Organisateur;
import com.tp.microservices.organisateur.persistence.repositories.OrganisateurRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganisateurProjection {

    private static final Logger logger = LoggerFactory.getLogger(OrganisateurProjection.class);
    
    private final OrganisateurRepository repository;

    public OrganisateurProjection(OrganisateurRepository repository) {
        this.repository = repository;
    }

    // ========== EVENT HANDLERS (mise à jour du read model) ==========

    @EventHandler
    @Transactional
    public void on(OrganisateurCreatedEvent event) {
        logger.info("📊 [PROJECTION] Handling OrganisateurCreatedEvent for ID: {}", event.getId());
        
        Organisateur organisateur = new Organisateur();
        organisateur.setAggregateId(event.getId());
        organisateur.setNom(event.getNom());
        organisateur.setPrenom(event.getPrenom());
        organisateur.setEmail(event.getEmail());
        organisateur.setEvenementIds(event.getEvenementIds() != null ? 
                                     new ArrayList<>(event.getEvenementIds()) : new ArrayList<>());
        
        repository.save(organisateur);
        logger.info("✅ [PROJECTION] Organisateur saved with aggregate ID: {}", event.getId());
    }

    @EventHandler
    @Transactional
    public void on(OrganisateurUpdatedEvent event) {
        logger.info("📊 [PROJECTION] Handling OrganisateurUpdatedEvent for ID: {}", event.getId());
        
        repository.findByAggregateId(event.getId()).ifPresent(org -> {
            org.setNom(event.getNom());
            org.setPrenom(event.getPrenom());
            org.setEmail(event.getEmail());
            org.setEvenementIds(event.getEvenementIds() != null ? 
                               new ArrayList<>(event.getEvenementIds()) : org.getEvenementIds());
            repository.save(org);
            logger.info("✅ [PROJECTION] Organisateur updated with aggregate ID: {}", event.getId());
        });
    }

    @EventHandler
    @Transactional
    public void on(OrganisateurDeletedEvent event) {
        logger.info("📊 [PROJECTION] Handling OrganisateurDeletedEvent for ID: {}", event.getId());
        
        repository.deleteByAggregateId(event.getId());
        logger.info("✅ [PROJECTION] Organisateur deleted with aggregate ID: {}", event.getId());
    }

    @EventHandler
    @Transactional
    public void on(EvenementAddedToOrganisateurEvent event) {
        logger.info("📊 [PROJECTION] Handling EvenementAddedToOrganisateurEvent for ID: {}", event.getId());
        
        repository.findByAggregateId(event.getId()).ifPresent(org -> {
            if (!org.getEvenementIds().contains(event.getEvenementId())) {
                org.getEvenementIds().add(event.getEvenementId());
                repository.save(org);
                logger.info("✅ [PROJECTION] Evenement {} added to Organisateur {}", 
                           event.getEvenementId(), event.getId());
            }
        });
    }

    // ========== QUERY HANDLERS (lecture du read model) ==========

    @QueryHandler
    public OrganisateurDTO handle(GetOrganisateurByIdQuery query) {
        logger.info("🔍 [QUERY] Handling GetOrganisateurByIdQuery for ID: {}", query.getId());
        
        return repository.findByAggregateId(query.getId())
                .map(org -> toDTO(org))
                .orElse(null);
    }

    @QueryHandler
    public List<OrganisateurDTO> handle(GetAllOrganisateursQuery query) {
        logger.info("🔍 [QUERY] Handling GetAllOrganisateursQuery");
        
        return repository.findAll().stream()
                .map(org -> toDTO(org))
                .collect(Collectors.toList());
    }

    @QueryHandler
    public OrganisateurDTO handle(GetOrganisateurByEmailQuery query) {
        logger.info("🔍 [QUERY] Handling GetOrganisateurByEmailQuery for email: {}", query.getEmail());
        
        return repository.findByEmail(query.getEmail())
                .map(org -> toDTO(org))
                .orElse(null);
    }

    // ========== HELPER METHODS ==========

    private OrganisateurDTO toDTO(Organisateur organisateur) {
        return new OrganisateurDTO(
                organisateur.getAggregateId(),
                organisateur.getNom(),
                organisateur.getPrenom(),
                organisateur.getEmail(),
                organisateur.getEvenementIds()
        );
    }
}