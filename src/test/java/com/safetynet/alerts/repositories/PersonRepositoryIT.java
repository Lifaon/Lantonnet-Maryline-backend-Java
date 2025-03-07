package com.safetynet.alerts.repositories;

import com.safetynet.alerts.models.Person;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@PropertySource("classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryIT {

    @Autowired
    private PersonRepository repository;

    static final private Person _person = new Person();

    @BeforeAll
    static public void setUp() {
        _person.setFirstName("John");
        _person.setLastName("Smith");
        _person.setAddress("Toto");
        _person.setCity("London");
        _person.setZip("12345");
        _person.setEmail("john.smith@gmail.com");
        _person.setPhone("0606060606");
    }

    @AfterAll
    static public void tearDown(
        @Value("${com.safetynet.alerts.DBHandle.Path}") Path path
    ) throws Exception {
        Files.deleteIfExists(path);
    }

    @Test
    @Order(0)
    public void isEmptyTest() {
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    @Order(10)
    public void createTest() {
        repository.create(_person);
        assertEquals(repository.getAll(), List.of(_person));
        assertEquals(repository.get(_person), Optional.of(_person));
    }

    @Test
    @Order(11)
    public void createDuplicateTest() {
        assertThrows(
            ResponseStatusException.class,
            () -> repository.create(_person)
        );
    }

    @Test
    @Order(20)
    public void editTest() {
        _person.setAddress("New Address");
        _person.setCity("New City");
        _person.setZip("New Zip");
        _person.setPhone("New Phone");
        _person.setEmail("New Email");
        repository.edit(_person);
        assertEquals(repository.getAll(), List.of(_person));
        assertEquals(repository.get(_person), Optional.of(_person));
    }

    @Test
    @Order(21)
    public void editNotFoundTest() {
        Person person = new Person();
        person.setFirstName("Bob");
        person.setLastName("Smith");
        assertThrows(
            ResponseStatusException.class,
            () -> repository.edit(person)
        );
    }

    @Test
    @Order(30)
    public void deleteTest() {
        repository.delete(_person);
        assertTrue(repository.getAll().isEmpty());
        assertTrue(repository.get(_person).isEmpty());
    }

    @Test
    @Order(31)
    public void deleteNotFoundTest() {
        assertThrows(
            ResponseStatusException.class,
            () -> repository.delete(_person)
        );
    }
}


