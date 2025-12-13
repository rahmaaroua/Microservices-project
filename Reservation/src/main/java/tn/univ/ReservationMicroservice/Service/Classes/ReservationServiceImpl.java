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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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



    @Autowired
    private EvenementFeignClient evenementFeignClient;

    // Méthode pour récupérer un Evenement via Feign
    public tn.univ.ReservationMicroservice.Dto.EvenementDto getEvenement(int id) {
        return evenementFeignClient.getEvenementById(id);
    }

}
