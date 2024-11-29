package com.safetynet.alerts;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/person")
    public List<Person> getAll() {
        return personService.getAll();
    }

    @PostMapping("/person")
    public void createPerson(@RequestBody Person person) {
        personService.createPerson(person);
    }

    @PutMapping("/person")
    public void editPerson(@RequestBody Person person) {
        personService.editPerson(person);
    }

    @DeleteMapping("/person")
    public void deletePerson(@RequestBody ObjectNode node) {
        if (node.has("firstName") && node.has("lastName")) {
            personService.deletePerson(node.get("firstName").asText(), node.get("lastName").asText());
        }
    }
}
