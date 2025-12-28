package tn.univ.GestionEvenement.Controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.univ.GestionEvenement.cqrs.commands.CreateEvenementCommand;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/evenement/commands")
public class EvenementCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/create")
    public String createEvenement(@RequestBody EvenementDTO dto) {
        String id = UUID.randomUUID().toString();

        System.out.println("🔵 [CONTROLLER] Creating Evenement with ID: " + id);

        CreateEvenementCommand command = new CreateEvenementCommand(
                id,
                dto.getDescription(),
                dto.getDated(),
                dto.getDatef(),
                dto.getCout()
        );

        commandGateway.sendAndWait(command);

        return "✅ Evenement created with ID: " + id;
    }

    // DTO interne
    @lombok.Getter
    @lombok.Setter
    public static class EvenementDTO {
        private String description;
        private Date dated;
        private Date datef;
        private float cout;
    }
}