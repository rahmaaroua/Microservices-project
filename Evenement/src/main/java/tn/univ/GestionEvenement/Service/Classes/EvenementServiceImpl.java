package tn.univ.GestionEvenement.Service.Classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.univ.GestionEvenement.Entity.Evenement;
import tn.univ.GestionEvenement.Repository.EvenementRepository;
import tn.univ.GestionEvenement.Service.Interfaces.IEventService;

@Service
public class EvenementServiceImpl implements IEventService {

    @Autowired
    private EvenementRepository evenementRepository;

    /**
     * Question 2 – 1ère signature
     */
    @Override
    public void ajoutAffectEvenParticip(Evenement e, int idParticip) {
        // idParticip not used because Evenement has no relation
        evenementRepository.save(e);
    }

    /**
     * Question 2 – 2ème signature
     */
    @Override
    public void ajoutAffectEvenParticip(Evenement e) {
        evenementRepository.save(e);
    }
}