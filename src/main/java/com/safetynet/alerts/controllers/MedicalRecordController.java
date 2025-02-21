package com.safetynet.alerts.controllers;

import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.services.MedicalRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController extends BaseController<MedicalRecord, PersonName, MedicalRecordService> {

    MedicalRecordController(MedicalRecordService service) {
        super(service);
    }

    @DeleteMapping
    public void delete(@Validated PersonName param) {
        _service.delete(param);
    }
}
