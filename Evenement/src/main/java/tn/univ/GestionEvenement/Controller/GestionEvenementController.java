package tn.univ.GestionEvenement.Controller;

import org.springframework.beans.factory.annotation.Value; // <-- AJOUTER CET IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.univ.GestionEvenement.Entity.Evenement;
import tn.univ.GestionEvenement.Repository.EvenementRepository;
import tn.univ.GestionEvenement.Service.Interfaces.IEventService;

@RestController
@RequestMapping("/Evenement")
public class GestionEvenementController {

    @Autowired
    private IEventService evenServ;

    @PostMapping("/add")
    public Evenement addEvenement(@RequestBody Evenement e) {
        evenServ.ajoutAffectEvenParticip(e);
        return e;
    }
    @Value("${spring.datasource.url}") // <-- AJOUTER CECI
    private String databaseUrl;
    @Autowired
    private EvenementRepository evenementRepository;

    @GetMapping("/{id}")
    public Evenement getEvenementById(@PathVariable int id) {
        System.out.println(">>> URL de la base de données utilisée : " + databaseUrl); // <-- AJOUTER CETTE LIGNE

        System.out.println(">>> Recherche de l'événement avec l'ID : " + id); // LIGNE À AJOUTER

        Evenement evenement = evenementRepository.findById(id).orElse(null); // On change un peu pour voir

        if (evenement == null) {
            System.out.println(">>> Aucun événement trouvé avec l'ID : " + id); // LIGNE À AJOUTER
        } else {
            System.out.println(">>> Événement trouvé : " + evenement.getDescription()); // LIGNE À AJOUTER
        }

        return evenement; // Le retour peut être null, c'est pour le test
    }

    // ... votre méthode getEvenementById

  /*  // AJOUTER CETTE MÉTHODE TEMPORAIRE
    @GetMapping("/count")
    public String countEvenements() {
        // On compte le nombre d'événements dans la base de données
        long count = evenementRepository.count();

        // On retourne le résultat sous forme de texte simple
        return "Il y a " + count + " événement(s) dans la table 'evenement'.";
    }
*/
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/count")
    public String count() {
        long count = evenementRepository.count();  // Votre logique existante

        // Log pour voir quelle instance répond
        System.out.println("🔵 Requête traitée par l'instance sur le port : " + serverPort);

        return "Instance port " + serverPort + " - Count: " + count;
    }
}