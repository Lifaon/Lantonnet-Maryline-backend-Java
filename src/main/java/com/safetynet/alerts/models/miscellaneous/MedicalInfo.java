package com.safetynet.alerts.models.miscellaneous;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class MedicalInfo extends PersonName {
    private List<String> medications;
    private List<String> allergies;
}
