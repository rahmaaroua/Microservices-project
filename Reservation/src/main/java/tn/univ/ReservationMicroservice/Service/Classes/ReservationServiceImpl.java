/*package tn.univ.ReservationMicroservice.Service.Classes;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.univ.ReservationMicroservice.Repository.ReservationRepository;
import tn.univ.ReservationMicroservice.Service.Interfaces.IReservationService;
import tn.univ.ReservationMicroservice.Entity.Reservation;


@Service
public class ReservationServiceImpl implements IReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public List<Reservation> retrieveAllReservations() {
        List<Reservation> list = reservationRepository.findAll();
        System.out.println(" Nombre total de réservations récupérées : " + list.size());
        return list;
    }

    @Override
    public Reservation retrieveReservation(int idReservation) {
        Reservation r = reservationRepository.findById(idReservation).orElse(null);
        if (r != null) {
            System.out.println("🔍 Réservation trouvée : ID = " + r.getIdReservation());
        } else {
            System.out.println(" Aucune réservation trouvée avec l’ID " + idReservation);
        }
        return r;
    }

    @Override
    public Reservation addReservation(Reservation r) {
        Reservation saved = reservationRepository.save(r);
        System.out.println(" Nouvelle réservation ajoutée (ID = " + saved.getIdReservation() + ")");
        return saved;
    }

    @Override
    public Reservation updateReservation(Reservation r) {
        Reservation updated = reservationRepository.save(r);
        System.out.println(" Réservation mise à jour (ID = " + updated.getIdReservation() + ")");
        return updated;
    }

    @Override
    public void deleteReservation(int idReservation) {
        reservationRepository.deleteById(idReservation);
        System.out.println(" Réservation supprimée (ID = " + idReservation + ")");
    }
}*/
package tn.univ.ReservationMicroservice.Service.Classes;

import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.univ.ReservationMicroservice.Feign.EvenementDto;
import tn.univ.ReservationMicroservice.Feign.EvenementFeignClient;
import tn.univ.ReservationMicroservice.Repository.ReservationRepository;
import tn.univ.ReservationMicroservice.Service.Interfaces.IReservationService;
import tn.univ.ReservationMicroservice.Entity.Reservation;


@Service
public class ReservationServiceImpl implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;



    @Override
    public List<Reservation> retrieveAllReservations() {
        List<Reservation> list = reservationRepository.findAll();
        System.out.println("Nombre total de réservations récupérées : " + list.size());
        return list;
    }

    @Override
    public Reservation retrieveReservation(int idReservation) {
        Reservation r = reservationRepository.findById(idReservation).orElse(null);
        if (r != null) {
            System.out.println("🔍 Réservation trouvée : ID = " + r.getIdReservation());
        } else {
            System.out.println("Aucune réservation trouvée avec l’ID " + idReservation);
        }
        return r;
    }

    @Override
    public Reservation addReservation(Reservation r) {
        Reservation saved = reservationRepository.save(r);
        System.out.println(" Nouvelle réservation ajoutée (ID = " + saved.getIdReservation() + ")");
        return saved;
    }

    @Override
    public Reservation updateReservation(Reservation r) {
        Reservation updated = reservationRepository.save(r);
        System.out.println("Réservation mise à jour (ID = " + updated.getIdReservation() + ")");
        return updated;
    }

    @Override
    public void deleteReservation(int idReservation) {
        reservationRepository.deleteById(idReservation);
        System.out.println("Réservation supprimée (ID = " + idReservation + ")");
    }







    private final EvenementFeignClient evenementFeignClient;

    public ReservationServiceImpl(EvenementFeignClient evenementFeignClient) {
        this.evenementFeignClient = evenementFeignClient;
    }

/*

    @Override
    @CircuitBreaker(name = "evenementService", fallbackMethod = "getEvenementFallback")
    public EvenementDto getEvenement(int id) {
        return evenementFeignClient.getEvenementById(id);
    }
*/
    // Fallback si Evenement KO
    public EvenementDto getEvenementFallback(int id, Throwable t) {
        EvenementDto dto = new EvenementDto();
        dto.setNom("Evenement indisponible");
        dto.setDescription("Service Evenement en panne (fallback activé par Resilience4j)");
        return dto;
    }
private int compteur = 0;

    @Override
    public EvenementDto getEvenement(int id) {
        compteur++;
        // 70% de chance de lancer une exception pour tester Retry
        if (Math.random() < 0.7 || compteur <= 5) {
            throw new RuntimeException("Service événement temporairement indisponible");
        }
        // Sinon on retourne un événement fictif
        EvenementDto dto = new EvenementDto();
        dto.setNom("Événement " + id);
        dto.setDescription("Description de l'événement " + id);
        return dto;
    }

}
