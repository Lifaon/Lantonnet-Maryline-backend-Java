package com.safetynet.alerts.repositories;

import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.PersonName;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public class PersonRepository extends BaseRepository<Person> {

    PersonRepository() {
        super("persons", Person[].class);
    }

    public Optional<Person> getPerson(PersonName name) {
        return _models.stream().filter(p -> p.sameName(name)).findAny();
    }

    private Optional<Person> _getPersonVerbose(PersonName name) {
        Optional<Person> person = getPerson(name);
        if (person.isEmpty()) {
            LOGGER.warn("No such person: {}", name);
        }
        return person;
    }

    public void createPerson(Person person) {
        if (getPerson(person).isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Person already exists"
            );
        }
        _models.add(person);
        _updateDB();
    };

    public void editPerson(Person person) {
        _getPersonVerbose(person).ifPresent(prev -> {
            _models.set(_models.indexOf(prev), person);
            _updateDB();
        });
    };

    public void deletePerson(PersonName name) {
        _getPersonVerbose(name).ifPresent(person -> {
            _models.remove(person);
            _updateDB();
        });
    };
}
