package com.safetynet.alerts;

import java.util.List;

public record MedicalRecord(String firstName,
                            String lastName,
                            String birthdate,
                            List<String> medications,
                            List<String> allergies) {}
