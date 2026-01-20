package com.tp.microservices.organisateur.service;

import com.tp.microservices.organisateur.persistence.entities.Organisateur;
import com.tp.microservices.organisateur.persistence.repositories.OrganisateurRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrganisateurService {

    private static final Logger logger = LoggerFactory.getLogger(OrganisateurService.class);
    private final OrganisateurRepository organisateurRepository;
    private final RestTemplate restTemplate;

    private static final String EVENEMENT_SERVICE = "http://evenement-microservice/Evenement";

    public OrganisateurService(OrganisateurRepository organisateurRepository, RestTemplate restTemplate) {
        this.organisateurRepository = organisateurRepository;
        this.restTemplate = restTemplate;
    }

    // ===================== CREATE =====================
    public Organisateur createOrganisateur(Organisateur org) {
        logger.info("Création organisateur : {} {}", org.getNom(), org.getPrenom());
        return organisateurRepository.save(org);
    }

    // ===================== READ =====================
    public List<Organisateur> getAllOrganisateurs() {
        logger.info("Récupération de tous les organisateurs");
        return organisateurRepository.findAll();
    }

    public Optional<Organisateur> getOrganisateurById(int id) {
        logger.info("Récupération organisateur ID : {}", id);
        return organisateurRepository.findById(id);
    }

    // ===================== UPDATE =====================
    public Organisateur updateOrganisateur(int id, Organisateur updatedOrg) {
        logger.info("Mise à jour organisateur ID : {}", id);

        Organisateur existing = organisateurRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Organisateur avec ID " + id + " introuvable"));

        existing.setNom(updatedOrg.getNom());
        existing.setPrenom(updatedOrg.getPrenom());
        existing.setEmail(updatedOrg.getEmail());

        return organisateurRepository.save(existing);
    }

    // ===================== DELETE =====================
    public void deleteOrganisateur(int id) {
        logger.info("Suppression organisateur ID : {}", id);

        if (!organisateurRepository.existsById(id)) {
            throw new EntityNotFoundException("Organisateur avec ID " + id + " introuvable");
        }
        organisateurRepository.deleteById(id);
    }

    // ===================== EVENEMENT CALLS =====================
    
    /**
     * Appel vers le microservice Evenement avec résilience
     * Les annotations sont déjà dans le Controller, pas besoin de les dupliquer ici
     */
    public Map<String, Object> getEvenementById(int id) {
        String url = EVENEMENT_SERVICE + "/" + id;
        logger.info("Appel REST vers: {}", url);
        
        try {
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            logger.error("Erreur lors de l'appel à evenement-microservice pour ID {}: {}", id, e.getMessage());
            throw e; // Laisser Resilience4j gérer l'exception
        }
    }

    /**
     * Récupérer le nombre total d'événements
     */
    public String getEvenementCount() {
        String url = EVENEMENT_SERVICE + "/count";
        logger.info("Appel REST vers: {}", url);
        
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            logger.error("Erreur lors de l'appel à evenement-microservice/count: {}", e.getMessage());
            throw e; // Laisser Resilience4j gérer l'exception
        }
    }
}