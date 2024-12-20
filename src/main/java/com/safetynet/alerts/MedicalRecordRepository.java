package com.safetynet.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class MedicalRecordRepository {

    @Autowired
    private DBHandle dbHandle;
    private final String dbKey = "medicalrecords";
    private final List<MedicalRecord> medicalRecords = new ArrayList<>();

    @PostConstruct
    private void init() throws JsonProcessingException {
        final MedicalRecord[] values = dbHandle.get(dbKey, MedicalRecord[].class);
        if (values != null) {
            medicalRecords.addAll(Arrays.asList(values));
        }
    }

    public List<MedicalRecord> getAll() {
        return medicalRecords;
    };

    public void createMedicalRecord(MedicalRecord medicalRecord) {
        try {
            medicalRecords.add(medicalRecord);
            dbHandle.set(dbKey, medicalRecords);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
