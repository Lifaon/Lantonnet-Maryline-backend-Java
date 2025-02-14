package com.safetynet.alerts.repositories;

import com.safetynet.alerts.models.Firestation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public class FirestationRepository extends BaseRepository<Firestation, String> {

    FirestationRepository() {
        super("firestations", Firestation[].class);
    }

    @Override
    public Optional<Firestation> get(String address) {
        return _models.stream().filter(f -> f.getAddress().equals(address)).findAny();
    }

    public void deleteByNumber(String stationNumber) {
        LOGGER.debug("Call 'deleteByNumber()'");
        if (_models.removeIf(f -> f.getStation().equals(stationNumber))) {
            _updateDB();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    };
}

