package tn.univ.GestionEvenement.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.univ.GestionEvenement.Entity.Evenement;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Integer> {

    Evenement findByDescription(String description);
}