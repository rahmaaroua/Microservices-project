package tn.univ.GestionEvenement.Service.Interfaces;




import tn.univ.GestionEvenement.Entity.Evenement;

public interface IEventService {

    void ajoutAffectEvenParticip(Evenement e, int idParticip);

    void ajoutAffectEvenParticip(Evenement e);
}