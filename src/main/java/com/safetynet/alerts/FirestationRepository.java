package com.safetynet.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class FirestationRepository {

    @Autowired
    private DBHandle dbHandle;
    private final String dbKey = "firestations";
    private final List<Firestation> firestations = new ArrayList<>();

    @PostConstruct
    private void init() throws JsonProcessingException {
        final Firestation[] values = dbHandle.get(dbKey, Firestation[].class);
        if (values != null) {
            firestations.addAll(Arrays.asList(values));
        }
    }

    public List<Firestation> getAll() {
        return firestations;
    };

    public void createFirestation(Firestation firestation) {
        try {
            firestations.add(firestation);
            dbHandle.set(dbKey, firestations);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
