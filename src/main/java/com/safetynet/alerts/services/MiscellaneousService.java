package com.safetynet.alerts.services;

import com.safetynet.alerts.models.PersonName;
import com.safetynet.alerts.models.miscellaneous.ChildAlert;
import com.safetynet.alerts.models.miscellaneous.ChildInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiscellaneousService {
    @Autowired
    PersonService _personService;
    @Autowired
    MedicalRecordService _medicalRecordService;

    public ChildAlert getChildAlert(String address) {
        ChildAlert childAlert = new ChildAlert();

        for (PersonName name : _personService.getPeopleByAddress(address)) {
            int age = _medicalRecordService.getPersonAge(name);
            if (age <= 18) {
                childAlert.children.add(new ChildInfo(name, age));
            }
            else {
                childAlert.adults.add(name);
            }
        }

        return childAlert;
    };
}
