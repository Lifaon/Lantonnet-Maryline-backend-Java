package com.safetynet.alerts.models.miscellaneous;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PersonMedicalInfo extends MedicalInfo {
    private String address;
    private int age;
    private String email;
}
