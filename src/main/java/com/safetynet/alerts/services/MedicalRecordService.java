package com.safetynet.alerts.services;

import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository _medicalRecordRepository;

    public List<MedicalRecord> getMedicalRecordsFromLastName(String lastName) {
        return _medicalRecordRepository.getAll().stream().filter(
            medicalRecord -> medicalRecord.getLastName().equals(lastName)
        ).toList();
    };

    public Optional<MedicalRecord> getMedicalRecord(PersonName personName) {
        return _medicalRecordRepository.get(personName);
    }

    public void createMedicalRecord(MedicalRecord p) {
        _medicalRecordRepository.create(p);
    };

    public void editMedicalRecord(MedicalRecord p) {
        _medicalRecordRepository.edit(p);
    };

    public void deleteMedicalRecord(PersonName name) {
        _medicalRecordRepository.delete(name);
    };
}
