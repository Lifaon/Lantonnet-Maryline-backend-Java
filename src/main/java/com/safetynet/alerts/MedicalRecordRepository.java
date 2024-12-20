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
    private DBHandle _dbHandle;
    private final String _dbKey = "medicalrecords";
    private final List<MedicalRecord> _medicalRecords = new ArrayList<>();

    @PostConstruct
    private void init() throws JsonProcessingException {
        final MedicalRecord[] values = _dbHandle.get(_dbKey, MedicalRecord[].class);
        if (values != null) {
            _medicalRecords.addAll(Arrays.asList(values));
        }
    }

    public List<MedicalRecord> getAll() {
        return _medicalRecords;
    };

    public void createMedicalRecord(MedicalRecord medicalRecord) {
        try {
            _medicalRecords.add(medicalRecord);
            _dbHandle.set(_dbKey, _medicalRecords);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
