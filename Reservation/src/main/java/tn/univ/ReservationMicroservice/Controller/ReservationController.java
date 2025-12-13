package tn.univ.ReservationMicroservice.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.univ.ReservationMicroservice.Entity.Reservation;
import tn.univ.ReservationMicroservice.Service.Interfaces.IReservationService;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    IReservationService reservationService;

    @GetMapping("/retrieve-all")
    public List<Reservation> getReservations() {
        return reservationService.retrieveAllReservations();
    }

    @GetMapping("/retrieve/{id}")
    public Reservation retrieveReservation(@PathVariable("id") int idReservation) {
        return reservationService.retrieveReservation(idReservation);
    }

    @PostMapping("/add")
    public Reservation addReservation(@RequestBody Reservation r) {
        return reservationService.addReservation(r);
    }

    @PutMapping("/update")
    public Reservation updateReservation(@RequestBody Reservation r) {
        return reservationService.updateReservation(r);
    }

    @DeleteMapping("/remove/{id}")
    public void removeReservation(@PathVariable("id") int idReservation) {
        reservationService.deleteReservation(idReservation);
    }

    @GetMapping("/test/Evenement/{id}")
    public tn.univ.ReservationMicroservice.Dto.EvenementDto testEvenement(@PathVariable int id) {
        return reservationService.getEvenement(id);
    }

}