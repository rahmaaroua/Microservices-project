package com.tp.microservices.organisateur.service;

import com.tp.microservices.organisateur.persistence.entities.Organisateur;
import com.tp.microservices.organisateur.persistence.repositories.OrganisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;


import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class OrganisateurService {

    private final OrganisateurRepository organisateurRepository;
    private final RestTemplate restTemplate;

    private static final String EVENEMENT_SERVICE = "http://evenement-microservice/Evenement";

    public OrganisateurService(OrganisateurRepository organisateurRepository, RestTemplate restTemplate) {
        this.organisateurRepository = organisateurRepository;
        this.restTemplate = restTemplate;
    }

    // ===================== CREATE =====================
    public Organisateur createOrganisateur(Organisateur org) {
        System.out.println("[ORGANISATEUR-SERVICE] Création organisateur : "
                + org.getNom() + " " + org.getPrenom());
        return organisateurRepository.save(org);
    }

    // ===================== READ =====================
    public List<Organisateur> getAllOrganisateurs() {
        System.out.println("[ORGANISATEUR-SERVICE] Récupération de tous les organisateurs");
        return organisateurRepository.findAll();
    }

    public Optional<Organisateur> getOrganisateurById(int id) {
        System.out.println("[ORGANISATEUR-SERVICE] Récupération organisateur ID : " + id);
        return organisateurRepository.findById(id);
    }

    // ===================== UPDATE =====================
    public Organisateur updateOrganisateur(int id, Organisateur updatedOrg) {

        System.out.println("[ORGANISATEUR-SERVICE] Mise à jour organisateur ID : " + id);

        Organisateur existing = organisateurRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Organisateur avec ID " + id + " introuvable"));

        existing.setNom(updatedOrg.getNom());
        existing.setPrenom(updatedOrg.getPrenom());
        existing.setEmail(updatedOrg.getEmail());
        // ⚠️ no @ManyToOne, no external entities (microservices rule)

        return organisateurRepository.save(existing);
    }

    // ===================== DELETE =====================
    public void deleteOrganisateur(int id) {
        System.out.println("[ORGANISATEUR-SERVICE] Suppression organisateur ID : " + id);

        if (!organisateurRepository.existsById(id)) {
            throw new EntityNotFoundException("Organisateur avec ID " + id + " introuvable");
        }
        organisateurRepository.deleteById(id);
    }

// ===================== EVENEMENT CALLS =====================

    @CircuitBreaker(name = "evenementCB", fallbackMethod = "fallbackEvenement")
    @Retry(name = "evenementRetry")
    public Map<String, Object> getEvenementById(int id) {
        String url = EVENEMENT_SERVICE + "/" + id;
        return restTemplate.getForObject(url, Map.class);    }

    public Map<String, Object> fallbackEvenement(int id, Exception e) {
    return Map.of(
            "error", "Evenement service unavailable",
            "requestedId", id,
            "message", e.getMessage()
    );
}

    public String getEvenementCount() {
        String url = EVENEMENT_SERVICE + "/count";
        return restTemplate.getForObject(url, String.class);
    }
}
