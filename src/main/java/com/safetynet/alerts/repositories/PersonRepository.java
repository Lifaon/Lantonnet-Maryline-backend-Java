package com.safetynet.alerts.repositories;

import com.safetynet.alerts.DBHandle;
import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.PersonName;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class PersonRepository {

    @Autowired
    private DBHandle _dbHandle;
    private final String _dbKey = "persons";
    private final List<Person> _persons = new ArrayList<>();

    @PostConstruct
    private void init() throws JsonProcessingException {
        final Person[] values = _dbHandle.get(_dbKey, Person[].class);
        if (values != null) {
            _persons.addAll(Arrays.asList(values));
        }
    }

    public final List<Person> getAll() {
        return Collections.unmodifiableList(_persons);
    };

    private int _findPersonID(PersonName name) {
        return _persons.stream().filter(p ->
            p.firstName.equals(name.firstName) &&
            p.lastName.equals(name.lastName)
        ).findAny().map(_persons::indexOf).orElse(-1);
    }

    public Person getPerson(PersonName name) {
        int id =  _findPersonID(name);
        if (id >= 0) {
            return  _persons.get(id);
        }
        return null;
    };

    public void createPerson(Person person) {
        try {
            _persons.add(person);
            _dbHandle.set(_dbKey, _persons);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };

    public void editPerson(Person person) {
        try {
            int id = _findPersonID(person);
            if (id >= 0) {
                _persons.set(id, person);
                _dbHandle.set(_dbKey, _persons);
            }
            else {
                System.err.println("No such person");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };

    public void deletePerson(PersonName name) {
        try {
            int id = _findPersonID(name);
            if (id >= 0) {
                _persons.remove(id);
                _dbHandle.set(_dbKey, _persons);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
