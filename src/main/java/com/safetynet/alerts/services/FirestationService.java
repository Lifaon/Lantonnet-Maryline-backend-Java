package com.safetynet.alerts.services;

import com.safetynet.alerts.models.Firestation;
import com.safetynet.alerts.models.miscellaneous.PersonInfo;
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

    public List<String> getAddresses(List<String> stations) {
        return _firestationRepository.getAll().stream().filter(
            firestation -> stations.contains(firestation.station)
        ).map(firestation -> firestation.address).toList();
    }

    public String getStation(String address) {
        return _firestationRepository.getAll().stream().filter(
            f -> f.address.equals(address)
        ).map(firestation -> firestation.station).findFirst().orElse(null);
    }

    public List<PersonInfo> getPeopleInfo(String station) {
        final List<PersonInfo> people = new ArrayList<>();
        _firestationRepository.getAll().forEach(firestation ->  {
            if (firestation.station.equals(station)) {
                _personService.getPeopleByAddress(firestation.address).forEach(person ->
                    people.add(new PersonInfo(person))
                );
            }
        });
        return people;
    }

    public CoveredPeople getCoveredPeople(String station) {

        final CoveredPeople coveredPeople = new CoveredPeople();
        coveredPeople.people = getPeopleInfo(station);

        coveredPeople.people.forEach(person ->
            _medicalRecordService.getMedicalRecord(person).ifPresent(record -> {
                if (record.isAdult()) {
                    coveredPeople.adults++;
                } else {
                    coveredPeople.children++;
                }
            })
        );

        return coveredPeople;
    };

    public void createFirestation(Firestation p) {
        _firestationRepository.create(p);
    };

    public void editFirestation(Firestation p) {
        _firestationRepository.edit(p);
    };

    public void deleteFirestation(String address) {
        _firestationRepository.delete(address);
    };

    public void deleteFirestationsByNumber(String stationNumber) {
        _firestationRepository.deleteByNumber(stationNumber);
    };

}
