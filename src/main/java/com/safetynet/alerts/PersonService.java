package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAll() {
        return personRepository.getAll();
    };

    public void createPerson(Person p) {
        personRepository.createPerson(p);
    };

    public void editPerson(Person p) {
        personRepository.editPerson(p);
    };

    public void deletePerson(String firstName, String lastName) {
        personRepository.deletePerson(firstName, lastName);
    };
}
