package com.safetynet.alerts.models.miscellaneous;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PersonEmergencyInfo extends MedicalInfo {
    private String phone;
    private int age;
}
