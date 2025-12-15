package tn.univ.ReservationMicroservice.Service.Interfaces;



import tn.univ.ReservationMicroservice.Entity.Reservation;
import tn.univ.ReservationMicroservice.Feign.EvenementDto;

import java.util.List;

public interface IReservationService {
    List<Reservation> retrieveAllReservations();
    Reservation retrieveReservation(int idReservation);
    Reservation addReservation(Reservation r);
    Reservation updateReservation(Reservation r);
    void deleteReservation(int idReservation);

    EvenementDto getEvenement(int id);
}