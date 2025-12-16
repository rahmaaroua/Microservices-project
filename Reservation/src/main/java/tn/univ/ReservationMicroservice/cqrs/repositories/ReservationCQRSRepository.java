package tn.univ.ReservationMicroservice.cqrs.repositories;


import tn.univ.ReservationMicroservice.Entity.ReservationCQRS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationCQRSRepository extends JpaRepository<ReservationCQRS, String> {
}