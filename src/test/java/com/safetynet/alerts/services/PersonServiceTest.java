package com.safetynet.alerts.services;

import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService _personService;

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
        Optional<Person> person = _personService.get(_person);
        assert(person.isPresent() && person.equals(Optional.of(_person)));
    }

    @Test
    public void getPersonTestNotFound() {
        when(repository.get(any(PersonName.class)))
            .thenReturn(Optional.empty());
        Optional<Person> person = _personService.get(_person);
        assert(person.isEmpty());
    }

    @Test
    public void getPeopleByAddressTest() {
        when(repository.getAll()).thenReturn(_people);
        List<Person> people = _personService.getPeopleByAddress(_person.getAddress());
        assert(people.equals(_people));
    }

    @Test
    public void getPeopleByAddressTestNotFound() {
        when(repository.getAll()).thenReturn(_people);
        List<Person> people = _personService.getPeopleByAddress("Not found");
        assert(people.isEmpty());
    }

    @Test
    public void getCommunityEmailTest() {
        when(repository.getAll()).thenReturn(_people);
        Set<String> emails = _personService.getCommunityEmail(_person.getCity());
        assert(!emails.isEmpty() && emails.contains(_person.getEmail()));
    }

    @Test
    public void getCommunityEmailTestNotFound() {
        when(repository.getAll()).thenReturn(_people);
        Set<String> emails = _personService.getCommunityEmail("Not found");
        assert(emails.isEmpty());
    }
}
