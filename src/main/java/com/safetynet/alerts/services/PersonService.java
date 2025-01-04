package com.safetynet.alerts.services;

import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.PersonName;
import com.safetynet.alerts.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private PersonRepository _personRepository;

    public Person getPerson(PersonName personName) {
        return _personRepository.getPerson(personName);
    }

    public List<Person> getPeopleByAddress(String address) {
        return _personRepository.getAll().stream().filter(
            person -> person.address.equals(address)
        ).toList();
    }

    public Set<String> getCommunityEmail(String city) {
        return _personRepository.getAll().stream().filter(
            person -> person.city.equals(city)
        ).map(
            person -> person.email
        ).collect(Collectors.toSet());
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
