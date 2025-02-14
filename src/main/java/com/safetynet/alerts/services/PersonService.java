package com.safetynet.alerts.services;

import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private PersonRepository _personRepository;

    public Optional<Person> getPerson(PersonName personName) {
        return _personRepository.get(personName);
    }

    public List<Person> getPeopleByAddress(String address) {
        return _personRepository.getAll().stream().filter(
            person -> person.getAddress().equals(address)
        ).toList();
    }

    public Set<String> getCommunityEmail(String city) {
        return _personRepository.getAll().stream().filter(
            person -> person.getCity().equals(city)
        ).map(
            Person::getEmail
        ).collect(Collectors.toSet());
    }

    public void createPerson(Person p) {
        _personRepository.create(p);
    };

    public void editPerson(Person p) {
        _personRepository.edit(p);
    };

    public void deletePerson(PersonName name) {
        _personRepository.delete(name);
    };
}
