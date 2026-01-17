package com.tp.microservices.organisateur.controller;

import com.tp.microservices.organisateur.persistence.entities.Organisateur;
import com.tp.microservices.organisateur.service.OrganisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organisateurs")
public class OrganisateurController {

    private final OrganisateurService service;

    public OrganisateurController(OrganisateurService service) {
        this.service = service;
    }

    @GetMapping
    public List<Organisateur> getAll() {
        return service.getAllOrganisateurs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organisateur> getById(@PathVariable int id) {
        return service.getOrganisateurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Organisateur create(@RequestBody Organisateur org) {
        return service.createOrganisateur(org);
    }

    @PutMapping("/{id}")
    public Organisateur update(@PathVariable int id, @RequestBody Organisateur org) {
        return service.updateOrganisateur(id, org);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.deleteOrganisateur(id);
    }
}
