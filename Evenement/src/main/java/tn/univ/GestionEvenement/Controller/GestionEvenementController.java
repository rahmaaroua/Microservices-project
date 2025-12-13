package tn.univ.GestionEvenement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.univ.GestionEvenement.Entity.Evenement;
import tn.univ.GestionEvenement.Service.Interfaces.IEventService;

@RestController
@RequestMapping("/Evenement")
public class GestionEvenementController {

    @Autowired
    private IEventService evenServ;

    @PostMapping("/add")
    public Evenement addEvenement(@RequestBody Evenement e) {
        evenServ.ajoutAffectEvenParticip(e);
        return e;
    }
}
