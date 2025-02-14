package com.safetynet.alerts.services;

import com.safetynet.alerts.Utils;
import com.safetynet.alerts.models.miscellaneous.MedicalInfo;
import com.safetynet.alerts.models.miscellaneous.PersonInfo;
import com.safetynet.alerts.models.miscellaneous.PersonName;
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
            _medicalRecordService.getMedicalRecord(name).ifPresent(record -> {
                if (record.isAdult()) {
                    childAlert.getChildren().add(new ChildInfo(name, record.getAge()));
                } else {
                    childAlert.getAdults().add(new PersonName(name));
                }
            });
        });

        return childAlert;
    }

    public List<String> getPhoneAlert(String firestation) {
        return _firestationService.getPeopleInfo(firestation)
            .stream().map(PersonInfo::getPhone).toList();
    }

    private List<PersonEmergencyInfo> _getEmergencyInfo(String address) {
        List<PersonEmergencyInfo> people = new ArrayList<>();

        _personService.getPeopleByAddress(address).forEach(person -> {
            PersonEmergencyInfo new_person = new PersonEmergencyInfo();
            Utils.copyFields(PersonName.class, person, new_person);
            new_person.setPhone(person.getPhone());
            people.add(new_person);
        });

        people.forEach(person ->
                _medicalRecordService.getMedicalRecord(person).ifPresent(record -> {
                    Utils.copyDeclaredFields(MedicalInfo.class, record, person);
                    person.setAge(record.getAge());
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
            personMedicalInfo.setAge(record.getAge());
            ret.add(personMedicalInfo);
        });
        ret.forEach(personMedicalInfo ->
            _personService.getPerson(personMedicalInfo).ifPresent(person -> {
                personMedicalInfo.setAddress(person.getAddress());
                personMedicalInfo.setEmail(person.getEmail());
            })
        );
        return ret;
    }

    public Set<String> getCommunityEmail(String city) {
        return _personService.getCommunityEmail(city);
    }
}
