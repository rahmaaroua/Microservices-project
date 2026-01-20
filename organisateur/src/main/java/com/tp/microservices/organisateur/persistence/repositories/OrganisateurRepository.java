package com.tp.microservices.organisateur.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tp.microservices.organisateur.persistence.entities.Organisateur;

import java.util.Optional;

@Repository
public interface OrganisateurRepository extends JpaRepository<Organisateur, Integer> {
    
    Optional<Organisateur> findByAggregateId(String aggregateId);
    
    Optional<Organisateur> findByEmail(String email);
    
    void deleteByAggregateId(String aggregateId);
}