package tn.univ.GestionEvenement.Controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import tn.univ.GestionEvenement.Dto.ReservationDto;
import tn.univ.GestionEvenement.Entity.Evenement;
import tn.univ.GestionEvenement.Repository.EvenementRepository;
import tn.univ.GestionEvenement.Service.Interfaces.IEventService;

@RestController
@RequestMapping("/Evenement")
public class GestionEvenementController {

    @Autowired
    private IEventService evenServ;

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/add")
    public Evenement addEvenement(@RequestBody Evenement e) {
        evenServ.ajoutAffectEvenParticip(e);
        return e;
    }

    @GetMapping("/{id}")
    public Evenement getEvenementById(@PathVariable int id) {
        System.out.println(">>> URL de la base de données utilisée : " + databaseUrl);
        System.out.println(">>> Recherche de l'événement avec l'ID : " + id);

        Evenement evenement = evenementRepository.findById(id).orElse(null);

        if (evenement == null) {
            System.out.println(">>> Aucun événement trouvé avec l'ID : " + id);
        } else {
            System.out.println(">>> Événement trouvé : " + evenement.getDescription());
        }

        return evenement;
    }

    @GetMapping("/count")
    public String count() {
        long count = evenementRepository.count();
        System.out.println("🔵 Requête traitée par l'instance sur le port : " + serverPort);
        return "Instance port " + serverPort + " - Count: " + count;
    }

    // ========== MÉTHODES AVEC RESILIENCE4J ==========

    /**
     * Méthode principale : Récupère toutes les réservations depuis le microservice Reservation
     * Applique Retry, RateLimiter et CircuitBreaker
     */


    @GetMapping("/test-reservations")
    @Retry(name = "myRetry", fallbackMethod = "fallbackReservations")
    @CircuitBreaker(name = "reservationCircuitBreaker", fallbackMethod = "fallbackReservations")
    public ResponseEntity<String> getAllReservations() {
        System.out.println("🔵 [EVENEMENT PORT " + serverPort + "] Appel à Reservation");
        String url = "http://reservation-microservice/reservation/retrieve-all";
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok("✅ Réservations récupérées : " + response);
    }
    /**
     * Méthode avec ID : Récupère une réservation par ID
     */
    @GetMapping("/test-reservation/{id}")
    @Retry(name = "myRetry", fallbackMethod = "fallbackReservationById")
    @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallbackReservationById")
    @CircuitBreaker(name = "reservationCircuitBreaker", fallbackMethod = "fallbackReservationById")
    public ResponseEntity<String> getReservationById(@PathVariable int id) {
        System.out.println("🔄 Tentative de récupération de la réservation ID: " + id);

        String url = "http://reservation-microservice/reservation/retrieve/" + id;
        String response = restTemplate.getForObject(url, String.class);

        return ResponseEntity.ok("✅ Réservation " + id + " : " + response);
    }

    // ========== MÉTHODES FALLBACK ==========

    /**
     * Fallback pour getAllReservations
     * Appelée si Retry, RateLimiter ou CircuitBreaker échouent
     */
    public ResponseEntity<String> fallbackReservations(Exception e) {
        System.err.println("❌ FALLBACK activé pour getAllReservations : " + e.getMessage());

        String message = "⚠️ Service Reservation indisponible. " +
                "Veuillez réessayer plus tard. " +
                "Cause: " + e.getMessage();

        return ResponseEntity.ok(message);
    }

    /**
     * Fallback pour getReservationById
     */
    public ResponseEntity<String> fallbackReservationById(int id, Exception e) {
        System.err.println("❌ FALLBACK activé pour getReservationById(" + id + ") : " + e.getMessage());

        String message = "⚠️ Impossible de récupérer la réservation " + id + ". " +
                "Service temporairement indisponible. " +
                "Cause: " + e.getMessage();

        return ResponseEntity.ok(message);
    }

}