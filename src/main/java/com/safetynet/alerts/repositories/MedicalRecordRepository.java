package com.safetynet.alerts.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.alerts.DBHandle;
import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.PersonName;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    final private Logger LOGGER = LogManager.getLogger();

    @PostConstruct
    private void init() throws JsonProcessingException {
        _dbHandle.get(_dbKey, MedicalRecord[].class).ifPresent(values -> {
            _medicalRecords.addAll(Arrays.asList(values));
            LOGGER.debug("Imported {} medical records", _medicalRecords.size());
        });
    }

    public List<MedicalRecord> getAll() {
        return Collections.unmodifiableList(_medicalRecords);
    };

    public Optional<MedicalRecord> getMedicalRecord(PersonName name) {
        return _medicalRecords.stream().filter(r -> r.sameName(name)).findAny();
    };

    private Optional<MedicalRecord> _getMedicalRecordVerbose(PersonName name) {
        Optional<MedicalRecord> record = getMedicalRecord(name);
        if (record.isEmpty()) {
            LOGGER.warn("No such medical record: {}", name);
        }
        return record;
    };

    public void createMedicalRecord(MedicalRecord medicalRecord) {
        _medicalRecords.add(medicalRecord);
        _dbHandle.set(_dbKey, _medicalRecords);
    };

    public void editMedicalRecord(MedicalRecord medicalRecord) {
        _getMedicalRecordVerbose(medicalRecord).ifPresent(r -> {
            _medicalRecords.set(_medicalRecords.indexOf(r), medicalRecord);
            _dbHandle.set(_dbKey, _medicalRecords);
        });
    };

    public void deleteMedicalRecord(PersonName name) {
        _getMedicalRecordVerbose(name).ifPresent(r -> {
            _medicalRecords.remove(r);
            _dbHandle.set(_dbKey, _medicalRecords);
        });
    };
}
