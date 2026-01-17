package com.tp.microservices.organisateur.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tp.microservices.organisateur.persistence.entities.Organisateur;

@Repository
public interface OrganisateurRepository extends JpaRepository<Organisateur, Integer> {
    // You can add custom queries here if needed, e.g.,
    // Optional<Organisateur> findByEmail(String email);
}
