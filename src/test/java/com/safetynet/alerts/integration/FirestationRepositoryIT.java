package com.safetynet.alerts.integration;

import com.safetynet.alerts.models.Firestation;
import com.safetynet.alerts.repositories.FirestationRepository;
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
public class FirestationRepositoryIT {

    @Autowired
    private FirestationRepository repository;

    static final private Firestation _firestation = new Firestation();

    @BeforeAll
    static public void setUp() {
        _firestation.setStation("1");
        _firestation.setAddress("Toto Street");
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
        repository.create(_firestation);
        assertEquals(repository.getAll(), List.of(_firestation));
        assertEquals(repository.get(_firestation.getAddress()), Optional.of(_firestation));
    }

    @Test
    @Order(11)
    public void createDuplicateTest() {
        assertThrows(
            ResponseStatusException.class,
            () -> repository.create(_firestation)
        );
    }

    @Test
    @Order(20)
    public void editTest() {
        _firestation.setStation("2");
        repository.edit(_firestation);
        assertEquals(repository.getAll(), List.of(_firestation));
        assertEquals(repository.get(_firestation.getAddress()), Optional.of(_firestation));
    }

    @Test
    @Order(21)
    public void editNotFoundTest() {
        Firestation firestation = new Firestation();
        firestation.setStation("2");
        firestation.setAddress("Other Address");
        assertThrows(
            ResponseStatusException.class,
            () -> repository.edit(firestation)
        );
    }

    @Test
    @Order(30)
    public void deleteTest() {
        repository.delete(_firestation.getAddress());
        assertTrue(repository.getAll().isEmpty());
        assertTrue(repository.get(_firestation.getAddress()).isEmpty());
    }

    @Test
    @Order(31)
    public void deleteNotFoundTest() {
        assertThrows(
            ResponseStatusException.class,
            () -> repository.delete(_firestation.getAddress())
        );
    }

    @Test
    @Order(40)
    public void deleteByNumberTest() {
        createTest();
        repository.deleteByNumber(_firestation.getStation());
        assertTrue(repository.getAll().isEmpty());
        assertTrue(repository.get(_firestation.getAddress()).isEmpty());
    }

    @Test
    @Order(41)
    public void deleteByNumberNotFoundTest() {
        assertThrows(
            ResponseStatusException.class,
            () -> repository.deleteByNumber(_firestation.getAddress())
        );
    }
}


