package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.PersonName;
import com.safetynet.alerts.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    PersonService _personService;

    @PostMapping("/person")
    public void createPerson(@RequestBody Person person) {
        _personService.createPerson(person);
    }

    @PutMapping("/person")
    public void editPerson(@RequestBody Person person) {
        _personService.editPerson(person);
    }

    @DeleteMapping("/person")
    public void deletePerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        _personService.deletePerson(new PersonName(firstName, lastName));
    }
}
