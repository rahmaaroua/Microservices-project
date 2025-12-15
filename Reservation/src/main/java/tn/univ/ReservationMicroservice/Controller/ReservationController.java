package tn.univ.ReservationMicroservice.Controller;

import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.univ.ReservationMicroservice.Entity.Reservation;
import tn.univ.ReservationMicroservice.Feign.EvenementDto;
import tn.univ.ReservationMicroservice.Repository.ReservationRepository;
import tn.univ.ReservationMicroservice.Service.Interfaces.IReservationService;


@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    IReservationService reservationService;
    ReservationRepository reservationRepository ;


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

    /*@GetMapping("/test/Evenement/{id}")
    public tn.univ.ReservationMicroservice.Dto.EvenementDto testEvenement(@PathVariable int id) {
        return reservationService.getEvenement(id);
    }*/


/*
    @GetMapping("/test/Evenement/{id}")
    public ResponseEntity<EvenementDto> testEvenement(@PathVariable int id) {
        EvenementDto dto = reservationService.getEvenement(id);
        // on force HTTP 200 même si c'est un fallback
        return ResponseEntity.ok(dto);
    }

*/
@GetMapping("/test/Evenement/{id}")
@Retry(name = "myRetry", fallbackMethod = "fallback")
@RateLimiter(name = "myRateLimiter", fallbackMethod = "fallback")
@CircuitBreaker(name = "reservationCircuitBreaker", fallbackMethod = "fallback")
public ResponseEntity<EvenementDto> testEvenement(@PathVariable int id) {
    EvenementDto dto = reservationService.getEvenement(id);
    return ResponseEntity.ok(dto);
}

    // Fallback pour Retry, RateLimiter et CircuitBreaker
    public ResponseEntity<EvenementDto> fallback(int id, Exception e) {
        EvenementDto fallbackDto = new EvenementDto();
        fallbackDto.setNom("Service indisponible");
        fallbackDto.setDescription("Veuillez réessayer plus tard. Cause: " + e.getMessage());
        return ResponseEntity.ok(fallbackDto);
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/count-evenements")
    public String countEvenements() {
        String url = "http://evenement-microservice/Evenement/count";
        String response = restTemplate.getForObject(url, String.class);
        return "Nombre d'événements : " + response;
    }

    // Endpoint qui utilise RestTemplate (ne fonctionne pas actuellement)
    @GetMapping("/evenement/{id}")
    public String getEvenement(@PathVariable int id) {
        String url = "http://evenement-microservice/Evenement/" + id;
        return restTemplate.getForObject(url, String.class);
    }


    // Endpoint de test pour vérifier l'injection
    @GetMapping("/test-resttemplate")
    public String testRestTemplate() {
        return "RestTemplate est : " + (restTemplate == null ? "❌ NULL" : "✅ OK");
    }

}