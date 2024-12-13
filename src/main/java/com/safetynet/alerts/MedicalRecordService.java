package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getAll() {
        return medicalRecordRepository.getAll();
    };

    public void createMedicalRecord(MedicalRecord p) {
        medicalRecordRepository.createMedicalRecord(p);
    };

}
