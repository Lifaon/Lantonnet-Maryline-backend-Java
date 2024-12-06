package com.safetynet.alerts;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FirestationRepository {
    private final List<Firestation> firestations = new ArrayList<Firestation>();

    public void init(List<Firestation> values) {
        firestations.clear();
        firestations.addAll(values);
    }

    public List<Firestation> getAll() {
        return firestations;
    };
}
