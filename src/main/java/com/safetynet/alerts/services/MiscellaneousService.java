package com.safetynet.alerts.services;

import com.safetynet.alerts.Utils;
import com.safetynet.alerts.models.miscellaneous.ChildAlert;
import com.safetynet.alerts.models.miscellaneous.ChildInfo;
import com.safetynet.alerts.models.miscellaneous.MedicalInfo;
import com.safetynet.alerts.models.miscellaneous.PersonEmergencyInfo;
import com.safetynet.alerts.models.miscellaneous.PersonInfo;
import com.safetynet.alerts.models.miscellaneous.PersonMedicalInfo;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MiscellaneousService {
    private final PersonService _personService;
    private final MedicalRecordService _medicalRecordService;
    private final FirestationService _firestationService;

    public ChildAlert getChildAlert(String address) {
        ChildAlert childAlert = new ChildAlert();

        _personService.getPeopleByAddress(address).forEach(name -> {
            _medicalRecordService.get(name).ifPresent(record -> {
                if (record.isAdult()) {
                    childAlert.getAdults().add(new PersonName(name));
                } else {
                    childAlert.getChildren().add(new ChildInfo(name, record.getAge()));
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
            PersonEmergencyInfo personInfo = new PersonEmergencyInfo();
            _medicalRecordService.get(person).ifPresent(record -> {
                Utils.copyFields(MedicalInfo.class, record, personInfo);
                personInfo.setAge(record.getAge());
            });
            personInfo.setPhone(person.getPhone());
            people.add(personInfo);
        });

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
            _personService.get(record).ifPresent(person -> {
                personMedicalInfo.setAddress(person.getAddress());
                personMedicalInfo.setEmail(person.getEmail());
            });
            personMedicalInfo.setAge(record.getAge());
            ret.add(personMedicalInfo);
        });
        return ret;
    }

    public Set<String> getCommunityEmail(String city) {
        return _personService.getCommunityEmail(city);
    }
}
