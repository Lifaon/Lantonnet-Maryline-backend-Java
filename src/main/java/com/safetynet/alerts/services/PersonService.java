package com.safetynet.alerts.services;

import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.PersonName;
import com.safetynet.alerts.repositories.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository _personRepository;

    public List<Person> getPeopleByAddress(String address) {
        final List<Person> people = new ArrayList<>();
        for (Person person : _personRepository.getAll()) {
            if (person.address.equals(address)) {
                people.add(person);
            }
        }
        return people;
    }

    public void createPerson(Person p) {
        _personRepository.createPerson(p);
    };

    public void editPerson(Person p) {
        _personRepository.editPerson(p);
    };

    public void deletePerson(PersonName name) {
        _personRepository.deletePerson(name);
    };
}
