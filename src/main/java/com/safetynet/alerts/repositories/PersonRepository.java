package com.safetynet.alerts.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.alerts.DBHandle;
import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.PersonName;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    @PostConstruct
    private void init() throws JsonProcessingException {
        _dbHandle.get(_dbKey, Person[].class).ifPresent(values ->
            _people.addAll(Arrays.asList(values))
        );
    }

    public final List<Person> getAll() {
        return Collections.unmodifiableList(_people);
    };

    public Optional<Person> getPerson(PersonName name) {
        return _people.stream().filter(p -> p.sameName(name)).findAny();
    }

    public void createPerson(Person person) {
        _people.add(person);
        _dbHandle.set(_dbKey, _people);
    };

    public void editPerson(Person person) {
        getPerson(person).ifPresentOrElse(prev -> {
            _people.set(_people.indexOf(prev), person);
            _dbHandle.set(_dbKey, _people);
        }, () ->
            System.err.println("No such person")
        );
    };

    public void deletePerson(PersonName name) {
        getPerson(name).ifPresentOrElse(person -> {
            _people.remove(person);
            _dbHandle.set(_dbKey, _people);
        }, () ->
            System.err.println("No such person")
        );
    };
}
