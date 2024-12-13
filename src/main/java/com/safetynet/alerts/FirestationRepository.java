package com.safetynet.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

@Repository
public class FirestationRepository {

    @Autowired
    private DBHandle dbHandle;
    private final String dbKey = "firestations";
    private final List<Firestation> firestations = new ArrayList<>();

    @PostConstruct
    private void init() throws JsonProcessingException {
        final JsonNode node = dbHandle.getNode(dbKey);
        if (node != null && node.isArray()) {
            firestations.addAll(Arrays.asList(dbHandle.getMapper().treeToValue(node, Firestation[].class)));
        }
    }

    public List<Firestation> getAll() {
        return firestations;
    };

    public void createFirestation(Firestation firestation) {
        try {
            firestations.add(firestation);
            dbHandle.editNode(dbKey, firestations);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
