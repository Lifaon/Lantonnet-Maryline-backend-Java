package com.safetynet.alerts.services;

import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService extends BaseService<MedicalRecord, PersonName, MedicalRecordRepository> {
    @Autowired
    private MedicalRecordRepository _medicalRecordRepository;

    public List<MedicalRecord> getMedicalRecordsFromLastName(String lastName) {
        return _medicalRecordRepository.getAll().stream().filter(
            medicalRecord -> medicalRecord.getLastName().equals(lastName)
        ).toList();
    };
}
