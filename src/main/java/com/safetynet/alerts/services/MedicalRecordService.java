package com.safetynet.alerts.services;

import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.PersonName;
import com.safetynet.alerts.repositories.MedicalRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository _medicalRecordRepository;

    public List<MedicalRecord> getAll() {
        return _medicalRecordRepository.getAll();
    };

    public int getPersonAge(PersonName personName) {

        final MedicalRecord medicalRecord = _medicalRecordRepository.getMedicalRecord(personName);
        final LocalDate birth = LocalDate.parse(medicalRecord.birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return (int)java.time.temporal.ChronoUnit.YEARS.between(birth, LocalDate.now());
    }

    public boolean isAdult(PersonName personName) {
        return getPersonAge(personName) > 18;
    }

    public void createMedicalRecord(MedicalRecord p) {
        _medicalRecordRepository.createMedicalRecord(p);
    };

    public void editMedicalRecord(MedicalRecord p) {
        _medicalRecordRepository.editMedicalRecord(p);
    };

    public void deleteMedicalRecord(PersonName name) {
        _medicalRecordRepository.deleteMedicalRecord(name);
    };
}
