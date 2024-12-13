package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    @GetMapping("/medicalRecord")
    public List<MedicalRecord> getAll() {
        return medicalRecordService.getAll();
    }

    @PostMapping("/medicalRecord")
    public void createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.createMedicalRecord(medicalRecord);
    }
}
