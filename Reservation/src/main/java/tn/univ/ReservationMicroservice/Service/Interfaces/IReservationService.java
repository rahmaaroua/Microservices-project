package tn.univ.ReservationMicroservice.Service.Interfaces;



import tn.univ.ReservationMicroservice.Entity.Reservation;

import java.util.List;

public interface IReservationService {
    List<Reservation> retrieveAllReservations();
    Reservation retrieveReservation(int idReservation);
    Reservation addReservation(Reservation r);
    Reservation updateReservation(Reservation r);
    void deleteReservation(int idReservation);

    tn.univ.ReservationMicroservice.Dto.EvenementDto getEvenement(int id);
}