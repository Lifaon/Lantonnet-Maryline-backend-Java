package com.safetynet.alerts;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordRepository {
    private final List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();

    public void init(List<MedicalRecord> values) {
        medicalRecords.clear();
        medicalRecords.addAll(values);
    }

    public List<MedicalRecord> getAll() {
        return medicalRecords;
    };
}
