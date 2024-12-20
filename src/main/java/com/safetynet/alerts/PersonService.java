package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository _personRepository;

    public List<Person> getAll() {
        return _personRepository.getAll();
    };

    public void createPerson(Person p) {
        _personRepository.createPerson(p);
    };

    public void editPerson(Person p) {
        _personRepository.editPerson(p);
    };

    public void deletePerson(String firstName, String lastName) {
        _personRepository.deletePerson(firstName, lastName);
    };
}
