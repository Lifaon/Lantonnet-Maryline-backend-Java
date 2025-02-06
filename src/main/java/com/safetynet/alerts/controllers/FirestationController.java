package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.Firestation;
import com.safetynet.alerts.models.miscellaneous.CoveredPeople;
import com.safetynet.alerts.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/firestation")
public class FirestationController {

    @Autowired
    FirestationService _firestationService;

    @GetMapping
    public CoveredPeople getCoveredPeople(@RequestParam String stationNumber) {
        return _firestationService.getCoveredPeople(stationNumber);
    }

    @PostMapping
    public void createFirestation(@RequestBody Firestation firestation) {
        _firestationService.createFirestation(firestation);
    }

    @PutMapping
    public void editFirestation(@RequestBody Firestation firestation) {
        _firestationService.editFirestation(firestation);
    }

    @DeleteMapping
    public void deleteFirestation(@RequestParam(required = false) String address,
                                  @RequestParam(required = false) String stationNumber) {
        if (address == null && stationNumber == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "At least one parameter of 'address' or 'stationNumber' should be set."
            );
        }

        if (address != null) {
            _firestationService.deleteFirestation(address);
        } else {
            _firestationService.deleteFirestationsByNumber(stationNumber);
        }
    }
}
