package com.safetynet.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class MedicalRecordRepository {

    @Autowired
    private DBHandle dbHandle;
    private final String dbKey = "medicalRecords";
    private final List<MedicalRecord> medicalRecords = new ArrayList<>();

    @PostConstruct
    private void init() throws JsonProcessingException {
        final JsonNode node = dbHandle.getNode(dbKey);
        if (node != null && node.isArray()) {
            medicalRecords.addAll(Arrays.asList(dbHandle.getMapper().treeToValue(node, MedicalRecord[].class)));
        }
    }

    public List<MedicalRecord> getAll() {
        return medicalRecords;
    };

    public void createMedicalRecord(MedicalRecord medicalRecord) {
        try {
            medicalRecords.add(medicalRecord);
            dbHandle.editNode(dbKey, medicalRecords);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    };
}
