package com.safetynet.alerts.repositories;

import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.PersonName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MedicalRecordRepository extends BaseRepository<MedicalRecord> {

    MedicalRecordRepository() {
        super("medicalrecords", MedicalRecord[].class);
    }

    public Optional<MedicalRecord> getMedicalRecord(PersonName name) {
        return _models.stream().filter(r -> r.sameName(name)).findAny();
    };

    private Optional<MedicalRecord> _getMedicalRecordVerbose(PersonName name) {
        Optional<MedicalRecord> record = getMedicalRecord(name);
        if (record.isEmpty()) {
            LOGGER.warn("No such medical record: {}", name);
        }
        return record;
    };

    public void createMedicalRecord(MedicalRecord medicalRecord) {
        _models.add(medicalRecord);
        _updateDB();
    };

    public void editMedicalRecord(MedicalRecord medicalRecord) {
        _getMedicalRecordVerbose(medicalRecord).ifPresent(r -> {
            _models.set(_models.indexOf(r), medicalRecord);
            _updateDB();
        });
    };

    public void deleteMedicalRecord(PersonName name) {
        _getMedicalRecordVerbose(name).ifPresent(r -> {
            _models.remove(r);
            _updateDB();
        });
    };
}
