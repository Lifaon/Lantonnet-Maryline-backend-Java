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

    private int _findPersonID(String firstName, String lastName) {
        ListIterator<Person> it = _persons.listIterator();
        while (it.hasNext()) {
            final Person p = it.next();
            if (p.firstName().equals(firstName) && p.lastName().equals(lastName)) {
                return it.nextIndex()-1;
            }
        }
        return -1;
    };

    public Person getPerson(String firstName, String lastName) {
        int id = _findPersonID(firstName, lastName);
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
            int id = _findPersonID(person.firstName(), person.lastName());
            if (id > 0) {
                _persons.set(id, person);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };

    public void deletePerson(String firstName, String lastName) {
        try {
            int id = _findPersonID(firstName, lastName);
            if (id > 0) {
                _persons.remove(id);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
