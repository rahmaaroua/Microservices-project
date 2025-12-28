package tn.univ.ReservationMicroservice.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    private RestTemplate restTemplate;

    // ⭐ AJOUT : Récupérer le port de l'instance
    @Value("${server.port}")
    private String serverPort;

    // ⭐ MODIFIÉ : Retourne le port de l'instance dans la réponse
    @GetMapping("/retrieve-all")
    public ResponseEntity<Map<String, Object>> getReservations() {
        System.out.println("🔵 [RESERVATION PORT " + serverPort + "] Requête retrieve-all reçue");

        List<Reservation> reservations = reservationService.retrieveAllReservations();

        Map<String, Object> response = new HashMap<>();
        response.put("instancePort", serverPort);
        response.put("reservations", reservations);
        response.put("count", reservations.size());

        return ResponseEntity.ok(response);
    }

    // ⭐ MODIFIÉ : Ajoute un log avec le port
    @GetMapping("/retrieve/{id}")
    public ResponseEntity<Map<String, Object>> retrieveReservation(@PathVariable("id") int idReservation) {
        System.out.println("🔵 [RESERVATION PORT " + serverPort + "] Requête retrieve/" + idReservation + " reçue");

        Reservation reservation = reservationService.retrieveReservation(idReservation);

        Map<String, Object> response = new HashMap<>();
        response.put("instancePort", serverPort);
        response.put("reservation", reservation);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public Reservation addReservation(@RequestBody Reservation r) {
        System.out.println("🔵 [RESERVATION PORT " + serverPort + "] Ajout d'une réservation");
        return reservationService.addReservation(r);
    }

    @PutMapping("/update")
    public Reservation updateReservation(@RequestBody Reservation r) {
        System.out.println("🔵 [RESERVATION PORT " + serverPort + "] Mise à jour d'une réservation");
        return reservationService.updateReservation(r);
    }

    @DeleteMapping("/remove/{id}")
    public void removeReservation(@PathVariable("id") int idReservation) {
        System.out.println("🔵 [RESERVATION PORT " + serverPort + "] Suppression de la réservation " + idReservation);
        reservationService.deleteReservation(idReservation);
    }

    @GetMapping("/test/Evenement/{id}")
    @Retry(name = "myRetry", fallbackMethod = "fallback")
    @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallback")
    @CircuitBreaker(name = "reservationCircuitBreaker", fallbackMethod = "fallback")
    public ResponseEntity<EvenementDto> testEvenement(@PathVariable int id) {
        System.out.println("🔵 [RESERVATION PORT " + serverPort + "] Test Evenement " + id);
        EvenementDto dto = reservationService.getEvenement(id);
        return ResponseEntity.ok(dto);
    }

    // Fallback pour Retry, RateLimiter et CircuitBreaker
    public ResponseEntity<EvenementDto> fallback(int id, Exception e) {
        System.err.println("❌ [RESERVATION PORT " + serverPort + "] Fallback activé pour Evenement " + id);
        EvenementDto fallbackDto = new EvenementDto();
        fallbackDto.setNom("Service indisponible");
        fallbackDto.setDescription("Veuillez réessayer plus tard. Cause: " + e.getMessage());
        return ResponseEntity.ok(fallbackDto);
    }

    @GetMapping("/count-evenements")
    public String countEvenements() {
        System.out.println("🔵 [RESERVATION PORT " + serverPort + "] Count événements demandé");
        String url = "http://evenement-microservice/Evenement/count";
        String response = restTemplate.getForObject(url, String.class);
        return "[Instance " + serverPort + "] Nombre d'événements : " + response;
    }

    @GetMapping("/evenement/{id}")
    public String getEvenement(@PathVariable int id) {
        System.out.println("🔵 [RESERVATION PORT " + serverPort + "] Get Evenement " + id);
        String url = "http://evenement-microservice/Evenement/" + id;
        return "[Instance " + serverPort + "] " + restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/test-resttemplate")
    public String testRestTemplate() {
        return "[Instance " + serverPort + "] RestTemplate est : " + (restTemplate == null ? "❌ NULL" : "✅ OK");
    }
}