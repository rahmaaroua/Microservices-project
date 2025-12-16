package tn.univ.ReservationMicroservice.cqrs.events;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCQRSCreatedEvent {
    private String idReservation;
    private Date dateReservation;
    private boolean confirme;
}