package com.safetynet.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

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
            ListIterator<Person> it = _persons.listIterator();
            while (it.hasNext()) {
                Person p = it.next();
                if (p.firstName().equals(person.firstName()) && p.lastName().equals(person.lastName())) {
                    it.set(person);
                    _dbHandle.set(_dbKey, _persons);
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };

    public void deletePerson(String firstName, String lastName) {
        try {
            if (_persons.removeIf(e -> e.firstName().equals(firstName) && e.lastName().equals(lastName))) {
                _dbHandle.set(_dbKey, _persons);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
