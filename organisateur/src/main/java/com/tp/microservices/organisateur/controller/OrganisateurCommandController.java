package com.tp.microservices.organisateur.controller;

import com.tp.microservices.organisateur.cqrs.commands.*;
import com.tp.microservices.organisateur.dto.CreateOrganisateurDTO;
import com.tp.microservices.organisateur.dto.OrganisateurDTO;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/organisateurs/commands")
public class OrganisateurCommandController {

    private static final Logger logger = LoggerFactory.getLogger(OrganisateurCommandController.class);
    
    private final CommandGateway commandGateway;

    public OrganisateurCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    // ========== CREATE ==========
    
    @PostMapping
    public CompletableFuture<ResponseEntity<String>> createOrganisateur(
            @Valid @RequestBody CreateOrganisateurDTO dto) {
        
        logger.info("🔵 [COMMAND] Creating Organisateur: {} {}", dto.getNom(), dto.getPrenom());
        
        String id = UUID.randomUUID().toString();
        
        CreateOrganisateurCommand command = new CreateOrganisateurCommand(
                id,
                dto.getNom(),
                dto.getPrenom(),
                dto.getEmail(),
                dto.getEvenementIds()
        );
        
        return commandGateway.send(command)
                .thenApply(result -> {
                    logger.info("✅ [COMMAND] Organisateur created with ID: {}", id);
                    return ResponseEntity.status(HttpStatus.CREATED).body(id);
                })
                .exceptionally(ex -> {
                    logger.error("❌ [COMMAND] Error creating organisateur: {}", ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error: " + ex.getMessage());
                });
    }

    // ========== UPDATE ==========
    
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> updateOrganisateur(
            @PathVariable String id,
            @Valid @RequestBody OrganisateurDTO dto) {
        
        logger.info("🔵 [COMMAND] Updating Organisateur ID: {}", id);
        
        UpdateOrganisateurCommand command = new UpdateOrganisateurCommand(
                id,
                dto.getNom(),
                dto.getPrenom(),
                dto.getEmail(),
                dto.getEvenementIds()
        );
        
        return commandGateway.send(command)
                .thenApply(result -> {
                    logger.info("✅ [COMMAND] Organisateur updated: {}", id);
                    return ResponseEntity.ok("Organisateur mis à jour avec succès");
                })
                .exceptionally(ex -> {
                    logger.error("❌ [COMMAND] Error updating organisateur: {}", ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error: " + ex.getMessage());
                });
    }

    // ========== DELETE ==========
    
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteOrganisateur(@PathVariable String id) {
        
        logger.info("🔵 [COMMAND] Deleting Organisateur ID: {}", id);
        
        DeleteOrganisateurCommand command = new DeleteOrganisateurCommand(id);
        
        return commandGateway.send(command)
                .thenApply(result -> {
                    logger.info("✅ [COMMAND] Organisateur deleted: {}", id);
                    return ResponseEntity.ok("Organisateur supprimé avec succès");
                })
                .exceptionally(ex -> {
                    logger.error("❌ [COMMAND] Error deleting organisateur: {}", ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error: " + ex.getMessage());
                });
    }

    // ========== ADD EVENEMENT ==========
    
    @PostMapping("/{id}/evenements/{evenementId}")
    public CompletableFuture<ResponseEntity<String>> addEvenement(
            @PathVariable String id,
            @PathVariable Integer evenementId) {
        
        logger.info("🔵 [COMMAND] Adding Evenement {} to Organisateur {}", evenementId, id);
        
        AddEvenementToOrganisateurCommand command = 
            new AddEvenementToOrganisateurCommand(id, evenementId);
        
        return commandGateway.send(command)
                .thenApply(result -> {
                    logger.info("✅ [COMMAND] Evenement {} added to Organisateur {}", evenementId, id);
                    return ResponseEntity.ok("Evenement ajouté avec succès");
                })
                .exceptionally(ex -> {
                    logger.error("❌ [COMMAND] Error adding evenement: {}", ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error: " + ex.getMessage());
                });
    }
}