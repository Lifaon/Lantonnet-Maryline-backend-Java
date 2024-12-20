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
    public void init() throws JsonProcessingException {
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

    private ListIterator<Person> findPerson(String firstName, String lastName) {
        ListIterator<Person> iterator = _persons.listIterator();
        while (iterator.hasNext()) {
            Person next = iterator.next();
            if (next.firstName().equals(firstName) && next.lastName().equals(lastName)) {
                return iterator;
            }
        }
        return null;
    };

    public void editPerson(Person person) {
        ListIterator<Person> iterator = findPerson(person.firstName(), person.lastName());
        if (iterator != null) {
            try {
                iterator.set(person);
                _dbHandle.set(_dbKey, _persons);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    };

    public void deletePerson(String firstName, String lastName) {
        ListIterator<Person> iterator = findPerson(firstName, lastName);
        if (iterator != null) {
            try {
                iterator.remove();
                _dbHandle.set(_dbKey, _persons);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    };
}
