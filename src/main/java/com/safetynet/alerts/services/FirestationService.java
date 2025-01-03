package com.safetynet.alerts.services;

import com.safetynet.alerts.models.Firestation;
import com.safetynet.alerts.models.PersonInfo;
import com.safetynet.alerts.models.miscellaneous.CoveredPeople;
import com.safetynet.alerts.repositories.FirestationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirestationService {
    @Autowired
    private FirestationRepository _firestationRepository;
    @Autowired
    private PersonService _personService;
    @Autowired
    private MedicalRecordService _medicalRecordService;

    public List<PersonInfo> getStationPersonInfo(String station) {
        final List<PersonInfo> people = new ArrayList<>();
        _firestationRepository.getAll().forEach(firestation ->  {
            if (firestation.station().equals(station)) {
                people.addAll(_personService.getPeopleByAddress(firestation.address()));
            }
        });
        return people;
    }

    public CoveredPeople getCoveredPeople(String station) {

        final CoveredPeople coveredPeople = new CoveredPeople();
        coveredPeople.people = getStationPersonInfo(station);

        coveredPeople.people.forEach(person -> {
            if (_medicalRecordService.isAdult(person)) {
                coveredPeople.adults++;
            } else {
                coveredPeople.children++;
            }
        });

        return coveredPeople;
    };

    public void createFirestation(Firestation p) {
        _firestationRepository.createFirestation(p);
    };

    public void editFirestation(Firestation p) {
        _firestationRepository.editFirestation(p);
    };

    public void deleteAddress(String address) {
        _firestationRepository.deleteAddress(address);
    };

    public void deleteStation(String station) {
        _firestationRepository.deleteStation(station);
    };

}
