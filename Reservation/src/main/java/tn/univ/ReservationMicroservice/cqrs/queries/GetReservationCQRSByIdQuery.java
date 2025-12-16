package tn.univ.ReservationMicroservice.cqrs.queries;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReservationCQRSByIdQuery {
    private String idReservation;
}