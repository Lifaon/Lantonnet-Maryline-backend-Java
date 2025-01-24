package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.miscellaneous.ChildAlert;
import com.safetynet.alerts.models.miscellaneous.PersonEmergencyInfo;
import com.safetynet.alerts.models.miscellaneous.PersonMedicalInfo;
import com.safetynet.alerts.services.MiscellaneousService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class MiscellaneousController {
    @Autowired
    MiscellaneousService _miscellaneousService;

    @GetMapping("/childAlert")
    public ChildAlert getChildAlert(@RequestParam String address) {
        return _miscellaneousService.getChildAlert(address);
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneAlert(@RequestParam String firestation) {
        return _miscellaneousService.getPhoneAlert(firestation);
    }

    @GetMapping("/fire")
    public Pair<String, List<PersonEmergencyInfo>> getFireInfo(@RequestParam String address) {
        return _miscellaneousService.getFireInfo(address);
    }

    @GetMapping("/flood/stations")
    public Map<String, List<PersonEmergencyInfo>> getFloodStations(@RequestParam List<String> stations) {
        return _miscellaneousService.getFloodStations(stations);
    }

    @GetMapping("/personInfolastName")
    public List<PersonMedicalInfo> getPeopleMedicalInfos(@RequestParam String lastname) {
        return _miscellaneousService.getPeopleMedicalInfos(lastname);
    }

    @GetMapping("/communityEmail")
    public Set<String> getCommunityEmail(@RequestParam String city) {
        return _miscellaneousService.getCommunityEmail(city);
    }
}
