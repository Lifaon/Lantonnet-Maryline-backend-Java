package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class FirestationService {
    @Autowired
    private FirestationRepository _firestationRepository;
    @Autowired
    private PersonRepository _personRepository;
    @Autowired
    private MedicalRecordRepository _medicalRecordRepository;

    public CoveredPeople getCoveredPeople(String station) {
        final var firestations = _firestationRepository.getAll();
        final var people = _personRepository.getAll();

        final List<String> addresses = new ArrayList<>();
        for (final var firestation : firestations) {
            if (firestation.station().equals(station)) {
                addresses.add(firestation.address());
            }
        }

        final CoveredPeople coveredPeople = new CoveredPeople();
        for (final var person : people) {
            if (addresses.contains(person.address())) {
                coveredPeople.people.add(new CustomPerson(
                    person.firstName(),
                    person.lastName(),
                    person.address(),
                    person.phone()
                ));
            }
        }

        final LocalDate today = LocalDate.now();
        for (final var coveredPerson : coveredPeople.people) {

            final MedicalRecord record = _medicalRecordRepository.getMedicalRecord(coveredPerson.firstName(), coveredPerson.lastName());
            final LocalDate birth = LocalDate.parse(record.birthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            if (java.time.temporal.ChronoUnit.YEARS.between(birth, today) < 18) {
                coveredPeople.children++;
            }
            else {
                coveredPeople.adults++;
            }
        }

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
