package com.safetynet.alerts.services;

import com.safetynet.alerts.models.Firestation;
import com.safetynet.alerts.models.miscellaneous.CoveredPeople;
import com.safetynet.alerts.models.miscellaneous.PersonInfo;
import com.safetynet.alerts.repositories.FirestationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirestationService extends BaseService<Firestation, String, FirestationRepository> {
    private final PersonService _personService;
    private final MedicalRecordService _medicalRecordService;

    FirestationService(PersonService personService, MedicalRecordService medicalRecordService, FirestationRepository repository) {
        super(repository);
        _personService = personService;
        _medicalRecordService = medicalRecordService;
    }

    public List<String> getAddresses(List<String> stations) {
        return _repository.getAll().stream().filter(
            firestation -> stations.contains(firestation.getStation())
        ).map(Firestation::getAddress).toList();
    }

    public String getStation(String address) {
        return _repository.get(address).map(Firestation::getStation).orElse(null);
    }

    public List<PersonInfo> getPeopleInfo(String station) {
        final List<PersonInfo> people = new ArrayList<>();
        _repository.getAll().forEach(firestation ->  {
            if (firestation.getStation().equals(station)) {
                _personService.getPeopleByAddress(firestation.getAddress()).forEach(person ->
                    people.add(new PersonInfo(person))
                );
            }
        });
        return people;
    }

    public CoveredPeople getCoveredPeople(String station) {

        final CoveredPeople coveredPeople = new CoveredPeople();
        coveredPeople.setPeople(getPeopleInfo(station));
        coveredPeople.getPeople().forEach(person ->
            _medicalRecordService.get(person).ifPresent(record -> {
                if (record.isAdult()) {
                    coveredPeople.addAdult();
                } else {
                    coveredPeople.addChild();
                }
            })
        );

        return coveredPeople;
    };

    public void deleteByNumber(String stationNumber) {
        _repository.deleteByNumber(stationNumber);
    };

}
