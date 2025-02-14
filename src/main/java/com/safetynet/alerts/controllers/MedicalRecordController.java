package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    @Autowired
    MedicalRecordService _medicalRecordService;

    @PostMapping
    public void createMedicalRecord(@Validated @RequestBody MedicalRecord medicalRecord) {
        _medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PutMapping
    public void editMedicalRecord(@Validated @RequestBody MedicalRecord medicalRecord) {
        _medicalRecordService.editMedicalRecord(medicalRecord);
    }

    @DeleteMapping
    public void deleteMedicalRecord(@Validated PersonName name) {
        _medicalRecordService.deleteMedicalRecord(name);
    }
}
