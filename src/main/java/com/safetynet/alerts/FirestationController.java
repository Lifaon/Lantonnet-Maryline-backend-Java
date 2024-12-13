package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    @Autowired
    FirestationService firestationService;

    @GetMapping("/firestation")
    public List<Firestation> getAll() {
        return firestationService.getAll();
    }

    @PostMapping("/firestation")
    public void createFirestation(@RequestBody Firestation firestation) {
        firestationService.createFirestation(firestation);
    }
}
