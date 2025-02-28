package com.safetynet.alerts.services;

import com.safetynet.alerts.Utils;
import com.safetynet.alerts.models.Firestation;
import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.miscellaneous.CoveredPeople;
import com.safetynet.alerts.models.miscellaneous.PersonInfo;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.repositories.FirestationRepository;
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
public class FirestationServiceTest {
    @Mock
    private FirestationRepository repository;
    @Mock
    private PersonService personService;
    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private FirestationService service;

    final private Firestation _firestation = new Firestation();
    final private List<Firestation> _firestations = new ArrayList<>();
    final private Person _person = new Person();
    final private MedicalRecord _medicalRecord = new MedicalRecord();

    @BeforeEach
    public void setUp() {
        _firestation.setAddress("Toto");
        _firestation.setStation("2");

        _firestations.add(_firestation);

        _person.setFirstName("John");
        _person.setLastName("Smith");
        _person.setAddress(_firestation.getAddress());
        _person.setPhone("0606060606");

        _medicalRecord.setFirstName("John");
        _medicalRecord.setLastName("Smith");
        _medicalRecord.setBirthdate("01/01/1980");
    }

    @Test
    public void getFirestationTest() {
        when(repository.get(any(String.class)))
            .thenReturn(Optional.of(_firestation));
        Optional<Firestation> firestation = service.get(_firestation.getAddress());
        assertEquals(firestation, Optional.of(_firestation));
    }

    @Test
    public void getFirestationTestNotFound() {
        when(repository.get(any(String.class)))
            .thenReturn(Optional.empty());
        Optional<Firestation> firestation = service.get(_firestation.getAddress());
        assertTrue(firestation.isEmpty());
    }

    @Test
    public void createFirestationTest() {
        assertDoesNotThrow(() -> service.create(_firestation));
    }

    @Test
    public void createFirestationTestAlreadyExists() {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                .doNothing().when(repository).create(any(Firestation.class));
        assertThrows(ResponseStatusException.class, () -> service.create(_firestation));
    }

    @Test
    public void editFirestationTest() {
        assertDoesNotThrow(() -> service.edit(_firestation));
    }

    @Test
    public void editFirestationTestNotFound() {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                .doNothing().when(repository).edit(any(Firestation.class));
        assertThrows(ResponseStatusException.class, () -> service.edit(_firestation));
    }

    @Test
    public void deleteFirestationTest() {
        assertDoesNotThrow(() -> service.delete(_firestation.getAddress()));
    }

    @Test
    public void deleteFirestationTestAlreadyDeleted() {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .doNothing().when(repository).delete(any(String.class));
        assertThrows(ResponseStatusException.class, () -> service.delete(_firestation.getAddress()));
    }

    @Test
    public void getAddressesTest() {
        when(repository.getAll()).thenReturn(_firestations);
        List<String> firestations = service.getAddresses(List.of(_firestation.getStation()));
        assertIterableEquals(firestations, List.of(_firestation.getAddress()));
    }

    @Test
    public void getAddressesTestNotFound() {
        when(repository.getAll()).thenReturn(_firestations);
        var stations = new ArrayList<String>();
        stations.add("NotFound");
        var firestations = service.getAddresses(stations);
        assertTrue(firestations.isEmpty());
    }

    @Test
    public void getStationTest() {
        when(repository.get(any(String.class))).thenReturn(Optional.of(_firestation));
        String station = service.getStation(_firestation.getAddress());
        assertNotNull(station);
        assertEquals(station, _firestation.getStation());
    }

    @Test
    public void getStationTestNotFound() {
        when(repository.get(any(String.class))).thenReturn(Optional.empty());
        String station = service.getStation(_firestation.getAddress());
        assertNull(station);
    }

    @Test
    public void getPeopleInfoTest() {
        when(repository.getAll()).thenReturn(_firestations);
        when(personService.getPeopleByAddress(any(String.class))).thenReturn(List.of(_person));
        List<PersonInfo> peopleInfo = List.of(new PersonInfo(_person));
        List<PersonInfo> people = service.getPeopleInfo(_firestation.getStation());
        assertTrue(Utils.equals(PersonInfo.class, people, peopleInfo));
    }

    @Test
    public void getPeopleInfoTestNotFound() {
        when(repository.getAll()).thenReturn(_firestations);
        List<PersonInfo> people = service.getPeopleInfo("Not found");
        assertTrue(people.isEmpty());
    }

    @Test
    public void getCoveredPeopleTest() {
        when(repository.getAll()).thenReturn(_firestations);
        when(personService.getPeopleByAddress(any(String.class))).thenReturn(List.of(_person));
        when(medicalRecordService.get(any(PersonName.class))).thenReturn(Optional.of(_medicalRecord));
        List<PersonInfo> peopleInfo = List.of(new PersonInfo(_person));
        CoveredPeople people = service.getCoveredPeople(_firestation.getStation());
        assertEquals(people.getAdults(), 1);
        assertEquals(people.getChildren(), 0);
        assertTrue(Utils.equals(PersonInfo.class, people.getPeople(), peopleInfo));
    }

    @Test
    public void getCoveredPeopleTestNotFound() {
        when(repository.getAll()).thenReturn(_firestations);
        CoveredPeople people = service.getCoveredPeople("Not found");
        assertEquals(people.getAdults(), 0);
        assertEquals(people.getChildren(), 0);
        assertTrue(people.getPeople().isEmpty());
    }

    @Test
    public void deleteByNumberTest() {
        assertDoesNotThrow(() -> service.deleteByNumber(_firestation.getAddress()));
    }

    @Test
    public void deleteByNumberTestAlreadyDeleted() {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .doNothing().when(repository).deleteByNumber(any(String.class));
        assertThrows(ResponseStatusException.class, () -> service.deleteByNumber(_firestation.getAddress()));
    }

}
