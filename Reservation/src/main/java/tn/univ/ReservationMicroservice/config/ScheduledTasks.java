package tn.univ.ReservationMicroservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component // Indique à Spring que c'est un composant à gérer
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    // Exemple 1 : S'exécute toutes les 30 secondes (pour tester rapidement)
    @Scheduled(fixedRate = 30000) // 30000 millisecondes = 30 secondes
    public void reportCurrentTimeWithFixedRate() {
        log.info("Tâche planifiée : Exécution toutes les 30 secondes. Timestamp : {}", System.currentTimeMillis());
    }

    // Exemple 2 : S'exécute tous les jours à 10h15 du matin
    // C'est plus utile pour une vraie application (ex: nettoyer les vieilles réservations)
    @Scheduled(cron = "0 15 10 * * *")
    public void cleanUpOldReservations() {
        log.info("Tâche planifiée : Nettoyage des anciennes réservations à 10h15.");
        // Ici, vous mettriez votre logique pour nettoyer la base de données
        // Par exemple : reservationRepository.deleteOldReservations();
    }
}