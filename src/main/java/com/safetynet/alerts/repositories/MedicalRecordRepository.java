package com.safetynet.alerts.repositories;

import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.PersonName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MedicalRecordRepository extends BaseRepository<MedicalRecord, PersonName> {

    MedicalRecordRepository() {
        super("medicalrecords", MedicalRecord[].class);
    }

    @Override
    public Optional<MedicalRecord> get(PersonName name) {
        return _models.stream().filter(r -> r.sameName(name)).findAny();
    }
}
