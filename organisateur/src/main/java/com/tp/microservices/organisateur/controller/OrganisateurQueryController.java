package com.tp.microservices.organisateur.controller;

import com.tp.microservices.organisateur.cqrs.queries.*;
import com.tp.microservices.organisateur.dto.OrganisateurDTO;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/organisateurs/queries")
public class OrganisateurQueryController {

    private static final Logger logger = LoggerFactory.getLogger(OrganisateurQueryController.class);
    
    private final QueryGateway queryGateway;

    public OrganisateurQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    // ========== GET BY ID ==========
    
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<OrganisateurDTO>> getById(@PathVariable String id) {
        
        logger.info("🔍 [QUERY] Getting Organisateur by ID: {}", id);
        
        GetOrganisateurByIdQuery query = new GetOrganisateurByIdQuery(id);
        
        return queryGateway.query(query, ResponseTypes.instanceOf(OrganisateurDTO.class))
                .thenApply(result -> {
                    if (result != null) {
                        logger.info("✅ [QUERY] Organisateur found: {}", id);
                        return ResponseEntity.ok(result);
                    } else {
                        logger.warn("⚠️ [QUERY] Organisateur not found: {}", id);
                        return ResponseEntity.status(404).<OrganisateurDTO>build();
                    }
                })
                .exceptionally(ex -> {
                    logger.error("❌ [QUERY] Error getting organisateur: {}", ex.getMessage());
                    return ResponseEntity.status(500).<OrganisateurDTO>build();
                });
    }

    // ========== GET ALL ==========
    
    @GetMapping
    public CompletableFuture<ResponseEntity<List<OrganisateurDTO>>> getAll() {
        
        logger.info("🔍 [QUERY] Getting all Organisateurs");
        
        GetAllOrganisateursQuery query = new GetAllOrganisateursQuery();
        
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(OrganisateurDTO.class))
                .thenApply(result -> {
                    logger.info("✅ [QUERY] Found {} organisateurs", result.size());
                    return ResponseEntity.ok(result);
                })
                .exceptionally(ex -> {
                    logger.error("❌ [QUERY] Error getting all organisateurs: {}", ex.getMessage());
                    return ResponseEntity.status(500).<List<OrganisateurDTO>>build();
                });
    }

    // ========== GET BY EMAIL ==========
    
    @GetMapping("/email/{email}")
    public CompletableFuture<ResponseEntity<OrganisateurDTO>> getByEmail(@PathVariable String email) {
        
        logger.info("🔍 [QUERY] Getting Organisateur by email: {}", email);
        
        GetOrganisateurByEmailQuery query = new GetOrganisateurByEmailQuery(email);
        
        return queryGateway.query(query, ResponseTypes.instanceOf(OrganisateurDTO.class))
                .thenApply(result -> {
                    if (result != null) {
                        logger.info("✅ [QUERY] Organisateur found with email: {}", email);
                        return ResponseEntity.ok(result);
                    } else {
                        logger.warn("⚠️ [QUERY] Organisateur not found with email: {}", email);
                        return ResponseEntity.status(404).<OrganisateurDTO>build();
                    }
                })
                .exceptionally(ex -> {
                    logger.error("❌ [QUERY] Error getting organisateur by email: {}", ex.getMessage());
                    return ResponseEntity.status(500).<OrganisateurDTO>build();
                });
    }
}