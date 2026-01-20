package com.tp.microservices.organisateur.controller;

import com.tp.microservices.organisateur.persistence.entities.Organisateur;
import com.tp.microservices.organisateur.service.OrganisateurService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/organisateurs")
public class OrganisateurController {

    private static final Logger logger = LoggerFactory.getLogger(OrganisateurController.class);
    private final OrganisateurService service;

    public OrganisateurController(OrganisateurService service) {
        this.service = service;
    }

    // ===================== CRUD - AVEC Rate Limiter Strict =====================
    
    @GetMapping
    @RateLimiter(name = "crudRateLimiter", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<List<Organisateur>> getAll() {
        logger.info("✅ [GET ALL] Récupération de tous les organisateurs");
        return ResponseEntity.ok(service.getAllOrganisateurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organisateur> getById(@PathVariable int id) {
        logger.info("✅ [GET BY ID] Récupération organisateur ID: {}", id);
        return service.getOrganisateurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Organisateur> create(@RequestBody Organisateur org) {
        logger.info("✅ [CREATE] Création organisateur: {} {}", org.getNom(), org.getPrenom());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrganisateur(org));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organisateur> update(@PathVariable int id, @RequestBody Organisateur org) {
        logger.info("✅ [UPDATE] Mise à jour organisateur ID: {}", id);
        return ResponseEntity.ok(service.updateOrganisateur(id, org));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        logger.info("✅ [DELETE] Suppression organisateur ID: {}", id);
        service.deleteOrganisateur(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== EVENEMENT - AVEC Circuit Breaker & Retry =====================

    @GetMapping("/evenement/{id}")
    @CircuitBreaker(name = "evenementCB", fallbackMethod = "evenementFallback")
    @Retry(name = "evenementRetry")
    @RateLimiter(name = "evenementRateLimiter")
    public ResponseEntity<Map<String, Object>> getEvenement(@PathVariable int id) {
        logger.info("🔵 [EVENEMENT] Tentative d'appel vers evenement-microservice pour ID: {}", id);
        Map<String, Object> evenement = service.getEvenementById(id);
        logger.info("✅ [EVENEMENT] Succès de l'appel pour ID: {}", id);
        return ResponseEntity.ok(evenement);
    }

    @GetMapping("/evenement/count")
    @CircuitBreaker(name = "evenementCB", fallbackMethod = "countFallback")
    @Retry(name = "evenementRetry")
    @RateLimiter(name = "evenementRateLimiter")
    public ResponseEntity<String> getEvenementCount() {
        logger.info("🔵 [EVENEMENT COUNT] Tentative d'appel vers evenement-microservice/count");
        String count = service.getEvenementCount();
        logger.info("✅ [EVENEMENT COUNT] Succès: {}", count);
        return ResponseEntity.ok(count);
    }

    // ===================== FALLBACK METHODS =====================

    // Fallback pour Rate Limiter (CRUD)
    public ResponseEntity<List<Organisateur>> rateLimiterFallback(Exception e) {
        logger.warn("⚠️ [RATE LIMITER] Trop de requêtes détectées");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(Collections.emptyList());
    }

    // Fallback pour Evenement (Circuit Breaker + Retry)
    public ResponseEntity<Map<String, Object>> evenementFallback(int id, Exception e) {
        logger.error("❌ [FALLBACK] Échec total pour evenement ID: {} - {}", id, e.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Service evenement temporairement indisponible");
        response.put("requestedId", id);
        response.put("message", e.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    // Fallback pour Count
    public ResponseEntity<String> countFallback(Exception e) {
        logger.error("❌ [FALLBACK] Échec total pour evenement/count - {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Service evenement temporairement indisponible");
    }
}