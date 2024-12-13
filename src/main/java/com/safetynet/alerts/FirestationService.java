package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {
    @Autowired
    private FirestationRepository firestationRepository;

    public List<Firestation> getAll() {
        return firestationRepository.getAll();
    };

    public void createFirestation(Firestation p) {
        firestationRepository.createFirestation(p);
    };

}
