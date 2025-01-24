package com.safetynet.alerts.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.alerts.DBHandle;
import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.PersonName;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

    @Autowired
    private DBHandle _dbHandle;
    private final String _dbKey = "persons";
    private final List<Person> _people = new ArrayList<>();

    final private Logger LOGGER = LogManager.getLogger();

    @PostConstruct
    private void init() throws JsonProcessingException {
        _dbHandle.get(_dbKey, Person[].class).ifPresent(values -> {
            _people.addAll(Arrays.asList(values));
            LOGGER.debug("Imported {} people", _people.size());
        });
    }

    public final List<Person> getAll() {
        return Collections.unmodifiableList(_people);
    };

    public Optional<Person> getPerson(PersonName name) {
        return _people.stream().filter(p -> p.sameName(name)).findAny();
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
        _people.add(person);
        _dbHandle.set(_dbKey, _people);
    };

    public void editPerson(Person person) {
        _getPersonVerbose(person).ifPresent(prev -> {
            _people.set(_people.indexOf(prev), person);
            _dbHandle.set(_dbKey, _people);
        });
    };

    public void deletePerson(PersonName name) {
        _getPersonVerbose(name).ifPresent(person -> {
            _people.remove(person);
            _dbHandle.set(_dbKey, _people);
        });
    };
}
