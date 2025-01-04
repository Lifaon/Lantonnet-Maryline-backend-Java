package com.safetynet.alerts.services;

import com.safetynet.alerts.Utils;
import com.safetynet.alerts.models.MedicalInfo;
import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.PersonName;
import com.safetynet.alerts.models.miscellaneous.ChildAlert;
import com.safetynet.alerts.models.miscellaneous.ChildInfo;
import com.safetynet.alerts.models.miscellaneous.PersonEmergencyInfo;
import com.safetynet.alerts.models.miscellaneous.PersonMedicalInfo;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MiscellaneousService {
    @Autowired
    PersonService _personService;
    @Autowired
    MedicalRecordService _medicalRecordService;
    @Autowired
    FirestationService _firestationService;

    public ChildAlert getChildAlert(String address) {
        ChildAlert childAlert = new ChildAlert();

        _personService.getPeopleByAddress(address).forEach(name -> {
            int age = _medicalRecordService.getPersonAge(name);
            if (age <= 18) {
                childAlert.children.add(new ChildInfo(name, age));
            } else {
                childAlert.adults.add(new PersonName(name));
            }
        });

        return childAlert;
    }

    public List<String> getPhoneAlert(String firestation) {
        return _firestationService.getPeopleInfo(firestation).stream().map(
            personInfo -> personInfo.phone
        ).toList();
    }

    private List<PersonEmergencyInfo> _getEmergencyInfo(String address) {
        List<PersonEmergencyInfo> people = new ArrayList<>();

        _personService.getPeopleByAddress(address).forEach(person -> {
            PersonEmergencyInfo new_person = new PersonEmergencyInfo();
            Utils.copyFields(PersonName.class, person, new_person);
            new_person.phone = person.phone;
            people.add(new_person);
        });

        people.forEach(person ->
                _medicalRecordService.getMedicalRecord(person).ifPresent(record -> {
                    Utils.copyDeclaredFields(MedicalInfo.class, record, person);
                    person.age = record.getAge();
                })
        );

        return people;
    }

    public Pair<String, List<PersonEmergencyInfo>> getFireInfo(String address) {
        return new Pair<>(
            _firestationService.getStation(address),
            _getEmergencyInfo(address)
        );
    }

    public Map<String, List<PersonEmergencyInfo>> getFloodStations(List<String> stations) {
        Map<String, List<PersonEmergencyInfo>> map = new HashMap<>();
        _firestationService.getAddresses(stations).forEach(address -> {
            map.put(
                address,
                _getEmergencyInfo(address)
            );
        });
        return map;
    }

    public List<PersonMedicalInfo> getPeopleMedicalInfos(String lastName) {
        List<PersonMedicalInfo> ret = new ArrayList<>();
        _medicalRecordService.getMedicalRecordsFromLastName(lastName).forEach(record -> {
            PersonMedicalInfo personMedicalInfo = new PersonMedicalInfo();
            Utils.copyFields(MedicalInfo.class, record, personMedicalInfo);
            personMedicalInfo.age = record.getAge();
            ret.add(personMedicalInfo);
        });
        ret.forEach(personMedicalInfo -> {
            final Person person = _personService.getPerson(personMedicalInfo);
            personMedicalInfo.address = person.address;
            personMedicalInfo.email = person.email;
        });
        return ret;
    }

    public Set<String> getCommunityEmail(String city) {
        return _personService.getCommunityEmail(city);
    }
}
