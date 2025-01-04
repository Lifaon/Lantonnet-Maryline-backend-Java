package com.safetynet.alerts.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.alerts.DBHandle;
import com.safetynet.alerts.Utils;
import com.safetynet.alerts.models.Firestation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class FirestationRepository {

    @Autowired
    private DBHandle _dbHandle;
    private final String _dbKey = "firestations";
    private final List<Firestation> _firestations = new ArrayList<>();

    @PostConstruct
    private void init() throws JsonProcessingException {
        _dbHandle.get(_dbKey, Firestation[].class).ifPresent(values ->
            _firestations.addAll(Arrays.asList(values))
        );
    }

    public List<Firestation> getAll() {
        return Collections.unmodifiableList(_firestations);
    };

    public void createFirestation(Firestation firestation) {
        _firestations.add(firestation);
        _dbHandle.set(_dbKey, _firestations);
    };

    public void editFirestation(Firestation firestation) {
        _firestations.stream().filter(f -> f.address.equals(firestation.address)).findAny().ifPresent(f -> {
            Utils.copyFields(Firestation.class, firestation, f);
            _dbHandle.set(_dbKey, _firestations);
        });
    };

    public void deleteAddress(String address) {
        if (_firestations.removeIf(f -> f.address.equals(address))) {
            _dbHandle.set(_dbKey, _firestations);
        }
    };

    public void deleteStation(String station) {
        if (_firestations.removeIf(f -> f.station.equals(station))) {
            _dbHandle.set(_dbKey, _firestations);
        }
    };
}

