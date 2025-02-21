package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.Firestation;
import com.safetynet.alerts.models.miscellaneous.CoveredPeople;
import com.safetynet.alerts.services.FirestationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/firestation")
public class FirestationController extends BaseController<Firestation, String, FirestationService>  {

    FirestationController(FirestationService service) {
        super(service);
    }

    @GetMapping
    public CoveredPeople getCoveredPeople(@RequestParam String stationNumber) {
        return _service.getCoveredPeople(stationNumber);
    }

    @DeleteMapping
    public void delete(@RequestParam(required = false) String address,
                       @RequestParam(required = false) String stationNumber) {
        if (address == null && stationNumber == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "At least one parameter of 'address' or 'stationNumber' should be set."
            );
        }

        if (address != null) {
            _service.delete(address);
        } else {
            _service.deleteByNumber(stationNumber);
        }
    }
}
