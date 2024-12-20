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
    private DBHandle _dbHandle;
    private final String _dbKey = "firestations";
    private final List<Firestation> _firestations = new ArrayList<>();

    @PostConstruct
    private void init() throws JsonProcessingException {
        final Firestation[] values = _dbHandle.get(_dbKey, Firestation[].class);
        if (values != null) {
            _firestations.addAll(Arrays.asList(values));
        }
    }

    public List<Firestation> getAll() {
        return _firestations;
    };

    public void createFirestation(Firestation firestation) {
        try {
            _firestations.add(firestation);
            _dbHandle.set(_dbKey, _firestations);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
