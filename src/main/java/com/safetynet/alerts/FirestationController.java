package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
