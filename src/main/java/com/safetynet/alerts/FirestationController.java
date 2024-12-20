package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    @Autowired
    FirestationService _firestationService;

    @GetMapping("/firestation")
    public List<Firestation> getAll() {
        return _firestationService.getAll();
    }

    @PostMapping("/firestation")
    public void createFirestation(@RequestBody Firestation firestation) {
        _firestationService.createFirestation(firestation);
    }
}
