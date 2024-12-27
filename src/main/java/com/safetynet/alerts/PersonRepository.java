package com.safetynet.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public List<Person> getAll() {
        return _persons;
    };

    private int _findPersonID(PersonName name) {
        for (final Person p : _persons) {
            if (p.firstName.equals(name.firstName) && p.lastName.equals(name.lastName)) {
                return _persons.indexOf(p);
            }
        }
        return -1;
    }

    public Person getPerson(PersonName name) {
        int id =  _findPersonID(name);
        if (id > 0) {
            return _persons.get(id);
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
            if (id > 0) {
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
            if (id > 0) {
                _persons.remove(id);
                _dbHandle.set(_dbKey, _persons);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
