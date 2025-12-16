package tn.univ.ReservationMicroservice.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation_cqrs")  // Table différente !
public class ReservationCQRS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String idReservation;  // String UUID pour CQRS

    @Temporal(TemporalType.DATE)
    private Date dateReservation;

    private boolean confirme;

    public String getId() {
        return idReservation;
    }
}