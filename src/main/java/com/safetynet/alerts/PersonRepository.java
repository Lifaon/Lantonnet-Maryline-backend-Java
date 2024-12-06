package com.safetynet.alerts;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Repository
public class PersonRepository {
    private final List<Person> persons = new ArrayList<Person>();

    public void init(List<Person> values) {
        persons.clear();
        persons.addAll(values);
    };

    public List<Person> getAll() {
        return persons;
    };

    public void createPerson(Person person) {
        persons.add(person);
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
            iterator.set(person);
        }
    };

    public void deletePerson(String firstName, String lastName) {
        ListIterator<Person> iterator = findPerson(firstName, lastName);
        if (iterator != null) {
            iterator.remove();
        }
    };
}
