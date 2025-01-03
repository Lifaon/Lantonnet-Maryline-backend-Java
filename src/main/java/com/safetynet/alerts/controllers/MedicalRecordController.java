package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.PersonName;
import com.safetynet.alerts.services.MedicalRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
public class MedicalRecordController {

    @Autowired
    MedicalRecordService _medicalRecordService;

    @GetMapping("/medicalRecord")
    public List<MedicalRecord> getAll() {
        return _medicalRecordService.getAll();
    }

    @PostMapping("/medicalRecord")
    public void createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        _medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PutMapping("/medicalRecord")
    public void editMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        _medicalRecordService.editMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecord(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        _medicalRecordService.deleteMedicalRecord(new PersonName(firstName, lastName));
    }
}
