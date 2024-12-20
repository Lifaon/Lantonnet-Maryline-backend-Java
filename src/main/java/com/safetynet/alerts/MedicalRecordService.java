package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository _medicalRecordRepository;

    public List<MedicalRecord> getAll() {
        return _medicalRecordRepository.getAll();
    };

    public void createMedicalRecord(MedicalRecord p) {
        _medicalRecordRepository.createMedicalRecord(p);
    };

    public void editMedicalRecord(MedicalRecord p) {
        _medicalRecordRepository.editMedicalRecord(p);
    };

    public void deleteMedicalRecord(String firstName, String lastName) {
        _medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
    };
}
