package com.safetynet.alerts.repositories;

import com.safetynet.alerts.Utils;
import com.safetynet.alerts.models.Firestation;
import org.springframework.stereotype.Repository;

@Repository
public class FirestationRepository extends BaseRepository<Firestation> {

    FirestationRepository() {
        super("firestations", Firestation[].class);
    }

    public void createFirestation(Firestation firestation) {
        _models.add(firestation);
        _updateDB();
    };

    public void editFirestation(Firestation firestation) {
        _models.stream().filter(f -> f.address.equals(firestation.address)).findAny().ifPresent(f -> {
            Utils.copyFields(Firestation.class, firestation, f);
            _updateDB();
        });
    };

    public void deleteAddress(String address) {
        if (_models.removeIf(f -> f.address.equals(address))) {
            _updateDB();
        }
    };

    public void deleteStation(String station) {
        if (_models.removeIf(f -> f.station.equals(station))) {
            _updateDB();
        }
    };
}

