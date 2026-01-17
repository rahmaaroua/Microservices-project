package com.tp.microservices.organisateur.service;

import com.tp.microservices.organisateur.persistence.entities.Organisateur;
import com.tp.microservices.organisateur.persistence.repositories.OrganisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganisateurService {

    private final OrganisateurRepository repository;

    public OrganisateurService(OrganisateurRepository repository) {
        this.repository = repository;
    }

    public List<Organisateur> getAllOrganisateurs() {
        return repository.findAll();
    }

    public Optional<Organisateur> getOrganisateurById(int id) {
        return repository.findById(id);
    }

    public Organisateur createOrganisateur(Organisateur org) {
        return repository.save(org);
    }

    public Organisateur updateOrganisateur(int id, Organisateur org) {
        return repository.findById(id).map(existing -> {
            existing.setNom(org.getNom());
            existing.setPrenom(org.getPrenom());
            existing.setEmail(org.getEmail());
            existing.setEvenementIds(org.getEvenementIds());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Organisateur non trouvé"));
    }

    public void deleteOrganisateur(int id) {
        repository.deleteById(id);
    }
}
