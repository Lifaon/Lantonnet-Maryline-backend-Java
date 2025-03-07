package com.safetynet.alerts.repositories;

import com.safetynet.alerts.models.MedicalRecord;
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
public class MedicalRecordRepositoryIT {

    @Autowired
    private MedicalRecordRepository repository;

    static final private MedicalRecord _record = new MedicalRecord();

    @BeforeAll
    static public void setUp() {
        _record.setFirstName("John");
        _record.setLastName("Smith");
        _record.setBirthdate("01/01/1980");
        _record.setAllergies(List.of("Peanuts"));
        _record.setMedications(List.of("Estradiol"));
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
        repository.create(_record);
        assertEquals(repository.getAll(), List.of(_record));
        assertEquals(repository.get(_record), Optional.of(_record));
    }

    @Test
    @Order(11)
    public void createDuplicateTest() {
        assertThrows(
            ResponseStatusException.class,
            () -> repository.create(_record)
        );
    }

    @Test
    @Order(20)
    public void editTest() {
        _record.setBirthdate("01/01/2020");
        _record.setAllergies(List.of());
        _record.setMedications(List.of());
        repository.edit(_record);
        assertEquals(repository.getAll(), List.of(_record));
        assertEquals(repository.get(_record), Optional.of(_record));
    }

    @Test
    @Order(21)
    public void editNotFoundTest() {
        MedicalRecord record = new MedicalRecord();
        record.setFirstName("Bob");
        record.setLastName("Smith");
        assertThrows(
            ResponseStatusException.class,
            () -> repository.edit(record)
        );
    }

    @Test
    @Order(30)
    public void deleteTest() {
        repository.delete(_record);
        assertTrue(repository.getAll().isEmpty());
        assertTrue(repository.get(_record).isEmpty());
    }

    @Test
    @Order(31)
    public void deleteNotFoundTest() {
        assertThrows(
            ResponseStatusException.class,
            () -> repository.delete(_record)
        );
    }
}


