package com.safetynet.alerts.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safetynet.alerts.models.miscellaneous.MedicalInfo;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Setter
@Getter
public class MedicalRecord extends MedicalInfo implements Identifier<PersonName> {
    public String birthdate;

    @JsonIgnore
    public int getAge() {
        return (int)java.time.temporal.ChronoUnit.YEARS.between(
            LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy")),
            LocalDate.now()
        );
    }
    
    @JsonIgnore
    public boolean isAdult() {
        return getAge() > 18;
    }

    @Override
    public PersonName getIdentifier() {
        return this;
    }
}
