package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService _personService;

    @PostMapping
    public void createPerson(@RequestBody Person person) {
        _personService.createPerson(person);
    }

    @PutMapping
    public void editPerson(@RequestBody Person person) {
        _personService.editPerson(person);
    }

    @DeleteMapping
    public void deletePerson(@Validated PersonName name) {
        _personService.deletePerson(name);
    }
}
