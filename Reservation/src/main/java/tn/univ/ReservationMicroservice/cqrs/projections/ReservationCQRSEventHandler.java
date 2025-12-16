package tn.univ.ReservationMicroservice.cqrs.projections;


import org.axonframework.config.ProcessingGroup;
import tn.univ.ReservationMicroservice.Entity.ReservationCQRS;
import tn.univ.ReservationMicroservice.cqrs.events.ReservationCQRSCreatedEvent;
import tn.univ.ReservationMicroservice.cqrs.repositories.ReservationCQRSRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@ProcessingGroup("reservationProcessor")

@Component
public class ReservationCQRSEventHandler {

    @Autowired
    private ReservationCQRSRepository repository;

    @EventHandler
    public void on(ReservationCQRSCreatedEvent event) {
        ReservationCQRS reservation = new ReservationCQRS();
        reservation.setIdReservation(event.getIdReservation());
        reservation.setDateReservation(event.getDateReservation());
        reservation.setConfirme(event.isConfirme());

        repository.save(reservation);

        System.out.println("✅ [CQRS] Réservation sauvegardée : " + event.getIdReservation());
    }
}