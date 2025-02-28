package com.safetynet.alerts.services;

import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    final private Person _person = new Person();
    final private List<Person> _people = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        _person.setFirstName("John");
        _person.setLastName("Smith");
        _person.setAddress("Toto");
        _person.setCity("London");
        _person.setZip("12345");
        _person.setEmail("john.smith@gmail.com");
        _person.setPhone("0606060606");

        _people.add(_person);
    }

    @Test
    public void getPersonTest() {
        when(repository.get(any(PersonName.class)))
            .thenReturn(Optional.of(_person));
        Optional<Person> person = service.get(_person);
        assertEquals(person, Optional.of(_person));
    }

    @Test
    public void getPersonTestNotFound() {
        when(repository.get(any(PersonName.class)))
            .thenReturn(Optional.empty());
        Optional<Person> person = service.get(_person);
        assertTrue(person.isEmpty());
    }

    @Test
    public void createPersonTest() {
        assertDoesNotThrow(() -> service.create(_person));
    }

    @Test
    public void createPersonTestAlreadyExists() {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
            .doNothing().when(repository).create(any(Person.class));
        assertThrows(ResponseStatusException.class, () -> service.create(_person));
    }

    @Test
    public void editPersonTest() {
        assertDoesNotThrow(() -> service.edit(_person));
    }

    @Test
    public void editPersonTestNotFound() {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
            .doNothing().when(repository).edit(any(Person.class));
        assertThrows(ResponseStatusException.class, () -> service.edit(_person));
    }

    @Test
    public void deletePersonTest() {
        assertDoesNotThrow(() -> service.delete(_person));
    }

    @Test
    public void deletePersonTestAlreadyDeleted() {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
            .doNothing().when(repository).delete(any(Person.class));
        assertThrows(ResponseStatusException.class, () -> service.delete(_person));
    }

    @Test
    public void getPeopleByAddressTest() {
        when(repository.getAll()).thenReturn(_people);
        List<Person> people = service.getPeopleByAddress(_person.getAddress());
        assertEquals(people, _people);
    }

    @Test
    public void getPeopleByAddressTestNotFound() {
        when(repository.getAll()).thenReturn(_people);
        List<Person> people = service.getPeopleByAddress("Not found");
        assertTrue(people.isEmpty());
    }

    @Test
    public void getCommunityEmailTest() {
        when(repository.getAll()).thenReturn(_people);
        Set<String> emails = service.getCommunityEmail(_person.getCity());
        assertTrue(emails.contains(_person.getEmail()));
    }

    @Test
    public void getCommunityEmailTestNotFound() {
        when(repository.getAll()).thenReturn(_people);
        Set<String> emails = service.getCommunityEmail("Not found");
        assertTrue(emails.isEmpty());
    }
}
