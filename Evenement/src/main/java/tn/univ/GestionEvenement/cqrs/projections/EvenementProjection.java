package tn.univ.GestionEvenement.cqrs.projections;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tn.univ.GestionEvenement.Entity.Evenement;
import tn.univ.GestionEvenement.Repository.EvenementRepository;
import tn.univ.GestionEvenement.cqrs.events.EvenementCreatedEvent;
import tn.univ.GestionEvenement.cqrs.queries.GetEvenementByIdQuery;

@Component
public class EvenementProjection {

    @Autowired
    private EvenementRepository evenementRepository;

    // ========== EVENT HANDLER (Write Side → Read Side) ==========
    @EventHandler
    public void on(EvenementCreatedEvent event) {
        System.out.println("🔵 [PROJECTION] Handling EvenementCreatedEvent: " + event.getId());

        Evenement evenement = new Evenement();
        evenement.setId(Integer.parseInt(event.getId()));
        evenement.setDescription(event.getDescription());
        evenement.setDated(event.getDated());
        evenement.setDatef(event.getDatef());
        evenement.setCout(event.getCout());

        evenementRepository.save(evenement);

        System.out.println("✅ [PROJECTION] Evenement saved to MySQL: " + event.getId());
    }

    // ========== QUERY HANDLER (Read Side) ==========
    @QueryHandler
    public Evenement handle(GetEvenementByIdQuery query) {
        System.out.println("🔵 [PROJECTION] Handling GetEvenementByIdQuery: " + query.getId());
        return evenementRepository.findById(Integer.parseInt(query.getId())).orElse(null);
    }
}