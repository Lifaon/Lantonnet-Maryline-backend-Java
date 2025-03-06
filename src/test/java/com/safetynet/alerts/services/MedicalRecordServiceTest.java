package com.safetynet.alerts.services;

import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.repositories.MedicalRecordRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
    @Mock
    private MedicalRecordRepository repository;

    @InjectMocks
    private MedicalRecordService service;

    final private MedicalRecord _record = new MedicalRecord();
    final private List<MedicalRecord> _records = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        _record.setFirstName("John");
        _record.setLastName("Smith");

        _records.add(_record);
    }

    @Test
    public void getMedicalRecordTest() {
        when(repository.get(any(PersonName.class)))
                .thenReturn(Optional.of(_record));
        Optional<MedicalRecord> person = service.get(_record);
        assertEquals(person, Optional.of(_record));
    }

    @Test
    public void getMedicalRecordTestNotFound() {
        when(repository.get(any(PersonName.class)))
                .thenReturn(Optional.empty());
        Optional<MedicalRecord> person = service.get(_record);
        assertTrue(person.isEmpty());
    }

    @Test
    public void createMedicalRecordTest() {
        assertDoesNotThrow(() -> service.create(_record));
    }

    @Test
    public void createMedicalRecordTestAlreadyExists() {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                .doNothing().when(repository).create(any(MedicalRecord.class));
        assertThrows(ResponseStatusException.class, () -> service.create(_record));
    }

    @Test
    public void editMedicalRecordTest() {
        assertDoesNotThrow(() -> service.edit(_record));
    }

    @Test
    public void editMedicalRecordTestNotFound() {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                .doNothing().when(repository).edit(any(MedicalRecord.class));
        assertThrows(ResponseStatusException.class, () -> service.edit(_record));
    }

    @Test
    public void deleteMedicalRecordTest() {
        assertDoesNotThrow(() -> service.delete(_record));
    }

    @Test
    public void deleteMedicalRecordTestAlreadyDeleted() {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .doNothing().when(repository).delete(any(MedicalRecord.class));
        assertThrows(ResponseStatusException.class, () -> service.delete(_record));
    }

    @Test
    public void getMedicalRecordsFromLastNameTest() {
        when(repository.getAll()).thenReturn(_records);
        List<MedicalRecord> records = service.getMedicalRecordsFromLastName(_record.getLastName());
        assertEquals(records, _records);
    }

    @Test
    public void getMedicalRecordsFromLastNameTestNotFound() {
        when(repository.getAll()).thenReturn(_records);
        List<MedicalRecord> records = service.getMedicalRecordsFromLastName("Not Found");
        assertTrue(records.isEmpty());
    }
}
