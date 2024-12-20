package com.safetynet.alerts;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    PersonService _personService;

    @GetMapping("/person")
    public List<Person> getAll() {
        return _personService.getAll();
    }

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
        _personService.deletePerson(firstName, lastName);
    }
}
