package com.safetynet.alerts.repositories;

import com.safetynet.alerts.DBHandle;
import com.safetynet.alerts.models.Firestation;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

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
        return Collections.unmodifiableList(_firestations);
    };

    public void createFirestation(Firestation firestation) {
        try {
            _firestations.add(firestation);
            _dbHandle.set(_dbKey, _firestations);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };

    private ListIterator<Firestation> _findFirestation(String address) {
        ListIterator<Firestation> iterator = _firestations.listIterator();
        while (iterator.hasNext()) {
            Firestation next = iterator.next();
            if (next.address().equals(address)) {
                return iterator;
            }
        }
        return null;
    }

    public void editFirestation(Firestation firestation) {
        try {
            ListIterator<Firestation> it = _firestations.listIterator();
            while (it.hasNext()) {
                Firestation next = it.next();
                if (next.address().equals(firestation.address())) {
                    it.set(firestation);
                    _dbHandle.set(_dbKey, _firestations);
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };

    public void deleteAddress(String address) {
        try {
            if (_firestations.removeIf(e -> e.address().equals(address))) {
                _dbHandle.set(_dbKey, _firestations);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };

    public void deleteStation(String station) {
        try {
            if (_firestations.removeIf(e -> e.station().equals(station))) {
                _dbHandle.set(_dbKey, _firestations);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}

