package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.Firestation;
import com.safetynet.alerts.models.miscellaneous.CoveredPeople;
import com.safetynet.alerts.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirestationController {

    @Autowired
    FirestationService _firestationService;

    @GetMapping("/firestation")
    public CoveredPeople getCoveredPeople(@RequestParam(value = "stationNumber") String stationNumber) {
        return _firestationService.getCoveredPeople(stationNumber);
    }

    @PostMapping("/firestation")
    public void createFirestation(@RequestBody Firestation firestation) {
        _firestationService.createFirestation(firestation);
    }

    @PutMapping("/firestation")
    public void editFirestation(@RequestBody Firestation firestation) {
        _firestationService.editFirestation(firestation);
    }

    @DeleteMapping("/firestation")
    public void deleteFirestation(@RequestParam(required = false, value = "address") String address,
                                  @RequestParam(required = false, value = "station") String station) {
        if (address == null && station == null) {
            System.err.println("At least one of 'address' or 'station' parameters should be set.");
            return;
        }

        if (address != null) {
            _firestationService.deleteAddress(address);
        } else {
            _firestationService.deleteStation(station);
        }
    }
}
