package com.safetynet.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
    private DBHandle dbHandle;
    private final String dbKey = "persons";
    private final List<Person> persons = new ArrayList<>();

    @PostConstruct
    public void init() throws JsonProcessingException {
        final JsonNode node = dbHandle.getNode(dbKey);
        if (node != null && node.isArray()) {
            persons.addAll(Arrays.asList(dbHandle.getMapper().treeToValue(node, Person[].class)));
        }
    }

    public List<Person> getAll() {
        return persons;
    };

    public void createPerson(Person person) {
        try {
            persons.add(person);
            dbHandle.editNode(dbKey, persons);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };

    private ListIterator<Person> findPerson(String firstName, String lastName) {
        ListIterator<Person> iterator = persons.listIterator();
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
                dbHandle.editNode(dbKey, persons);
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
                dbHandle.editNode(dbKey, persons);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    };
}
