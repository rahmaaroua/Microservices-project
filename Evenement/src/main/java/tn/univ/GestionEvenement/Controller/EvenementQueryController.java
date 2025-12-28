package tn.univ.GestionEvenement.Controller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.univ.GestionEvenement.Entity.Evenement;
import tn.univ.GestionEvenement.cqrs.queries.GetEvenementByIdQuery;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/evenement/queries")
public class EvenementQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{id}")
    public CompletableFuture<Evenement> getEvenementById(@PathVariable String id) {
        System.out.println("🔵 [CONTROLLER] Query for Evenement ID: " + id);

        return queryGateway.query(
                new GetEvenementByIdQuery(id),
                ResponseTypes.instanceOf(Evenement.class)
        );
    }
}