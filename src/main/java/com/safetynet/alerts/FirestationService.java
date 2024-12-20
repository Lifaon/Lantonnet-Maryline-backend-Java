package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {
    @Autowired
    private FirestationRepository _firestationRepository;

    public List<Firestation> getAll() {
        return _firestationRepository.getAll();
    };

    public void createFirestation(Firestation p) {
        _firestationRepository.createFirestation(p);
    };

}
