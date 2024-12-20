package com.safetynet.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

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

    public void editMedicalRecord(MedicalRecord medicalRecord) {
        try {
            ListIterator<MedicalRecord> it = _medicalRecords.listIterator();
            while (it.hasNext()) {
                MedicalRecord r = it.next();
                if (r.firstName().equals(medicalRecord.firstName()) && r.lastName().equals(medicalRecord.lastName())) {
                    it.set(medicalRecord);
                    _dbHandle.set(_dbKey, _medicalRecords);
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };

    public void deleteMedicalRecord(String firstName, String lastName) {
        try {
            if (_medicalRecords.removeIf(e -> e.firstName().equals(firstName) && e.lastName().equals(lastName))) {
                _dbHandle.set(_dbKey, _medicalRecords);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
