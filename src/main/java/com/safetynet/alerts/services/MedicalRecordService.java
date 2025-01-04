package com.safetynet.alerts.services;

import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.PersonName;
import com.safetynet.alerts.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository _medicalRecordRepository;

    public List<MedicalRecord> getAll() {
        return _medicalRecordRepository.getAll();
    };

    public List<MedicalRecord> getMedicalRecordsFromLastName(String lastName) {
        return _medicalRecordRepository.getAll().stream().filter(
            medicalRecord -> medicalRecord.lastName.equals(lastName)
        ).toList();
    };

    public Optional<MedicalRecord> getMedicalRecord(PersonName personName) {
        return _medicalRecordRepository.getMedicalRecord(personName);
    }

    public int getPersonAge(PersonName personName) {
        return getMedicalRecord(personName).map(MedicalRecord::getAge).orElse(0);
    }

    public boolean isAdult(PersonName personName) {
        return getPersonAge(personName) > 18;
    }

    public void createMedicalRecord(MedicalRecord p) {
        _medicalRecordRepository.createMedicalRecord(p);
    };

    public void editMedicalRecord(MedicalRecord p) {
        _medicalRecordRepository.editMedicalRecord(p);
    };

    public void deleteMedicalRecord(PersonName name) {
        _medicalRecordRepository.deleteMedicalRecord(name);
    };
}
