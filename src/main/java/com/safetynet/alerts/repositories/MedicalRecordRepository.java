package com.safetynet.alerts.repositories;

import com.safetynet.alerts.DBHandle;
import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MedicalRecordRepository extends BaseRepository<MedicalRecord, PersonName> {

    MedicalRecordRepository(DBHandle handle) {
        super(handle, "medicalrecords", MedicalRecord[].class);
    }

    @Override
    public Optional<MedicalRecord> get(PersonName name) {
        return _models.stream().filter(r -> r.sameName(name)).findAny();
    }
}
