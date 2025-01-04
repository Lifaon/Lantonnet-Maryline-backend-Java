package com.safetynet.alerts.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MedicalRecord extends MedicalInfo {
    public String birthdate;

    public int getAge() {
        return (int)java.time.temporal.ChronoUnit.YEARS.between(
            LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy")),
            LocalDate.now()
        );
    }

    public boolean isAdult() {
        return getAge() > 18;
    }
};
