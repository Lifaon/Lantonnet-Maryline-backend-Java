package com.safetynet.alerts.models;

import java.util.List;

public class MedicalRecord extends PersonName {
    public String birthdate;
    public List<String> medications;
    public List<String> allergies;

    public MedicalRecord() {}

    public MedicalRecord(String firstName, String lastName, String birthdate, List<String> medications, List<String> allergies) {
        super(firstName, lastName);
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }
};
