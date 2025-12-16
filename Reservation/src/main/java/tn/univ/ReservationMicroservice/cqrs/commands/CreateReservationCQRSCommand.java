package tn.univ.ReservationMicroservice.cqrs.commands;



import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationCQRSCommand {

    @TargetAggregateIdentifier
    private String idReservation;
    private Date dateReservation;
    private boolean confirme;
}