package tn.univ.ReservationMicroservice.cqrs.projections;


import tn.univ.ReservationMicroservice.Entity.ReservationCQRS;
import tn.univ.ReservationMicroservice.cqrs.queries.GetAllReservationsCQRSQuery;
import tn.univ.ReservationMicroservice.cqrs.queries.GetReservationCQRSByIdQuery;
import tn.univ.ReservationMicroservice.cqrs.repositories.ReservationCQRSRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ReservationCQRSProjection {

    @Autowired
    private ReservationCQRSRepository repository;

    @QueryHandler
    public ReservationCQRS handle(GetReservationCQRSByIdQuery query) {
        return repository.findById(query.getIdReservation())
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
    }

    @QueryHandler
    public List<ReservationCQRS> handle(GetAllReservationsCQRSQuery query) {
        return repository.findAll();
    }
}