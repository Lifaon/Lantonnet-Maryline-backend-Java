package com.safetynet.alerts.services;

import com.safetynet.alerts.Utils;
import com.safetynet.alerts.models.Firestation;
import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.miscellaneous.ChildAlert;
import com.safetynet.alerts.models.miscellaneous.ChildInfo;
import com.safetynet.alerts.models.miscellaneous.MedicalInfo;
import com.safetynet.alerts.models.miscellaneous.PersonEmergencyInfo;
import com.safetynet.alerts.models.miscellaneous.PersonInfo;
import com.safetynet.alerts.models.miscellaneous.PersonMedicalInfo;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MiscellaneousServiceTest {
    @Mock
    private PersonService personService;
    @Mock
    private FirestationService firestationService;
    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private MiscellaneousService service;

    static final private Firestation _firestation = new Firestation();
    static final private List<Firestation> _firestations = new ArrayList<>();

    static final private Person _personA = new Person();
    static final private Person _personB = new Person();
    static final private List<Person> _people = new ArrayList<>();

    static final private MedicalRecord _recordA = new MedicalRecord();
    static final private MedicalRecord _recordB = new MedicalRecord();
    static final private List<MedicalRecord> _records = new ArrayList<>();

    static final private List<PersonEmergencyInfo> _personEmergencyInfos = new ArrayList<>();

    @BeforeAll
    static public void setUp() {
        _firestation.setAddress("Toto");
        _firestation.setStation("2");

        _firestations.add(_firestation);

        _personA.setFirstName("John");
        _personA.setLastName("Smith");
        _personA.setAddress(_firestation.getAddress());
        _personA.setPhone("0606060606");
        _personA.setEmail("john.smith@gmail.com");
        _people.add(_personA);

        Utils.copyFields(Person.class, _personA, _personB);
        _personB.setFirstName("Bob");
        _personB.setEmail("bob.smith@gmail.com");
        _people.add(_personB);

        Utils.copyFields(PersonName.class, _personA, _recordA);
        _recordA.setBirthdate("01/01/1980");
        _recordA.setMedications(List.of("Estradiol"));
        _records.add(_recordA);
        Utils.copyFields(PersonName.class, _personB, _recordB);
        _recordB.setBirthdate("01/01/2020");
        _recordB.setAllergies(List.of("Peanuts"));
        _records.add(_recordB);

        PersonEmergencyInfo infoA = new PersonEmergencyInfo();
        Utils.copyFields(MedicalInfo.class, _recordA, infoA);
        infoA.setPhone(_personA.getPhone());
        infoA.setAge(_recordA.getAge());
        _personEmergencyInfos.add(infoA);
        PersonEmergencyInfo infoB = new PersonEmergencyInfo();
        Utils.copyFields(MedicalInfo.class, _recordB, infoB);
        infoB.setPhone(_personB.getPhone());
        infoB.setAge(_recordB.getAge());
        _personEmergencyInfos.add(infoB);
    }

    @Test
    public void getChildAlertTest() {
        when(personService.getPeopleByAddress(any())).thenReturn(_people);
        lenient().when(medicalRecordService.get(_recordA)).thenReturn(Optional.of(_recordA));
        lenient().when(medicalRecordService.get(_recordB)).thenReturn(Optional.of(_recordB));
        ChildAlert childAlert = service.getChildAlert(_firestation.getAddress());
        assertTrue(Utils.equals(PersonName.class, childAlert.getAdults(), List.of(new PersonName(_personA))));
        assertTrue(Utils.equals(ChildInfo.class, childAlert.getChildren(), List.of(new ChildInfo(_recordB, _recordB.getAge()))));
    }

    @Test
    public void getPhoneAlertTest() {
        when(firestationService.getPeopleInfo(any())).thenReturn(List.of(new PersonInfo(_personA)));
        List<String> phones = service.getPhoneAlert(_firestation.getStation());
        assertEquals(phones, List.of(_personA.getPhone()));
    }


    @Test
    public void getFireInfoTest() {
        when(firestationService.getStation(any())).thenReturn(_firestation.getStation());
        when(personService.getPeopleByAddress(any())).thenReturn(_people);
        lenient().when(medicalRecordService.get(_personA)).thenReturn(Optional.of(_recordA));
        lenient().when(medicalRecordService.get(_personB)).thenReturn(Optional.of(_recordB));

        var fireInfo = service.getFireInfo(_firestation.getAddress());
        assertEquals(fireInfo.getKey(), _firestation.getStation());
        assertTrue(Utils.equals(PersonEmergencyInfo.class, fireInfo.getValue(), _personEmergencyInfos));
    }

    @Test
    public void getFloodStationsTest() {
        when(firestationService.getAddresses(List.of(_firestation.getStation()))).thenReturn(List.of(_firestation.getAddress()));
        when(personService.getPeopleByAddress(_firestation.getAddress())).thenReturn(_people);
        lenient().when(medicalRecordService.get(_personA)).thenReturn(Optional.of(_recordA));
        lenient().when(medicalRecordService.get(_personB)).thenReturn(Optional.of(_recordB));

        var stationsInfos = service.getFloodStations(List.of(_firestation.getStation()));
        assertFalse(stationsInfos.isEmpty());
        for (var fireInfo : stationsInfos.entrySet()) {
            assertEquals(fireInfo.getKey(), _firestation.getAddress());
            assertTrue(Utils.equals(PersonEmergencyInfo.class, fireInfo.getValue(), _personEmergencyInfos));
        }
    }

    @Test
    public void getPeopleMedicalInfosTest() {
        when(medicalRecordService.getMedicalRecordsFromLastName(_personA.getLastName())).thenReturn(_records);
        lenient().when(personService.get(_recordA)).thenReturn(Optional.of(_personA));
        lenient().when(personService.get(_recordB)).thenReturn(Optional.of(_personB));

        PersonMedicalInfo infoA = new PersonMedicalInfo();
        Utils.copyFields(MedicalInfo.class, _recordA, infoA);
        infoA.setEmail(_personA.getEmail());
        infoA.setAddress(_personA.getAddress());
        infoA.setAge(_recordA.getAge());
        PersonMedicalInfo infoB = new PersonMedicalInfo();
        Utils.copyFields(MedicalInfo.class, _recordB, infoB);
        infoB.setEmail(_personB.getEmail());
        infoB.setAddress(_personB.getAddress());
        infoB.setAge(_recordB.getAge());

        var infos = service.getPeopleMedicalInfos(_personA.getLastName());
        assertTrue(Utils.equals(PersonMedicalInfo.class, infos, List.of(infoA, infoB)));
    }



}
