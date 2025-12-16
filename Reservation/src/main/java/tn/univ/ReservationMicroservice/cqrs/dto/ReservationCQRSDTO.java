package tn.univ.ReservationMicroservice.cqrs.dto;


import lombok.Data;
import java.util.Date;

@Data
public class ReservationCQRSDTO {
    private Date dateReservation;
    private boolean confirme;
}