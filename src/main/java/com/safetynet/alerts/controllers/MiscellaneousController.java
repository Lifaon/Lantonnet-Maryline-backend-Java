package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.miscellaneous.ChildAlert;
import com.safetynet.alerts.services.MiscellaneousService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MiscellaneousController {
    @Autowired
    MiscellaneousService _miscellaneousService;

    @GetMapping("/childAlert")
    public ChildAlert getChildAlert(@RequestParam(value = "address") String address) {
        return _miscellaneousService.getChildAlert(address);
    }
}
