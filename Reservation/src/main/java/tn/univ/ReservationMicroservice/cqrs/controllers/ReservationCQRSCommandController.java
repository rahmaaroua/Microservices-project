package tn.univ.ReservationMicroservice.cqrs.controllers;


import tn.univ.ReservationMicroservice.cqrs.commands.CreateReservationCQRSCommand;
import tn.univ.ReservationMicroservice.cqrs.dto.ReservationCQRSDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
        import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/cqrs/reservations/commands")
@CrossOrigin("*")
public class ReservationCQRSCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/create")
    public CompletableFuture<String> createReservation(@RequestBody ReservationCQRSDTO dto) {
        String id = UUID.randomUUID().toString();

        CreateReservationCQRSCommand command = new CreateReservationCQRSCommand(
                id,
                dto.getDateReservation(),
                dto.isConfirme()
        );

        return commandGateway.send(command);
    }
}