package com.safetynet.alerts.repositories;

import com.safetynet.alerts.DBHandle;
import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.PersonName;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        return Collections.unmodifiableList(_medicalRecords);
    };

    private int _findMedicalRecordID(PersonName name) {
        return _medicalRecords.stream().filter(r ->
                r.firstName.equals(name.firstName) &&
                r.lastName.equals(name.lastName)
        ).findAny().map(_medicalRecords::indexOf).orElse(-1);
    };

    public Optional<MedicalRecord> getMedicalRecord(PersonName name) {
        int id = _findMedicalRecordID(name);
        if (id >= 0) {
            return Optional.ofNullable(_medicalRecords.get(id));
        }
        return Optional.empty();
    }

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
            int id = _findMedicalRecordID(medicalRecord);
            if (id >= 0) {
                _medicalRecords.set(id, medicalRecord);
                _dbHandle.set(_dbKey, _medicalRecords);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };

    public void deleteMedicalRecord(PersonName name) {
        try {
            int id = _findMedicalRecordID(name);
            if (id >= 0) {
                _medicalRecords.remove(id);
                _dbHandle.set(_dbKey, _medicalRecords);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
