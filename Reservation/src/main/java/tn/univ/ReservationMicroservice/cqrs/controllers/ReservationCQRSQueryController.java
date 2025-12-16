package tn.univ.ReservationMicroservice.cqrs.controllers;

import tn.univ.ReservationMicroservice.Entity.ReservationCQRS;
import tn.univ.ReservationMicroservice.cqrs.queries.GetAllReservationsCQRSQuery;
import tn.univ.ReservationMicroservice.cqrs.queries.GetReservationCQRSByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
        import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/cqrs/reservations/queries")
@CrossOrigin("*")
public class ReservationCQRSQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{id}")
    public CompletableFuture<ReservationCQRS> getById(@PathVariable String id) {
        return queryGateway.query(
                new GetReservationCQRSByIdQuery(id),
                ResponseTypes.instanceOf(ReservationCQRS.class)
        );
    }

    @GetMapping("/all")
    public CompletableFuture<List<ReservationCQRS>> getAll() {
        return queryGateway.query(
                new GetAllReservationsCQRSQuery(),
                ResponseTypes.multipleInstancesOf(ReservationCQRS.class)
        );
    }
}

