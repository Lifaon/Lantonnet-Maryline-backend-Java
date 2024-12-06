package com.safetynet.alerts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.*;

@Configuration
public class DBHandle {

    static final private String json_path = "./resources/data.json";

    @NotNull
    static private String readJSON() {
        try {
            StringBuilder json_data = new StringBuilder();
            File f = new File(json_path);
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                json_data.append(reader.nextLine());
            }
            reader.close();
            return json_data.toString();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return "{}";
        }
    }

    @Bean
    CommandLineRunner init(
            PersonRepository personRepository,
            FirestationRepository firestationRepository,
            MedicalRecordRepository medicalRecordRepository)
    {
        return args -> {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(readJSON());

            JsonNode persons = node.get("persons");
            if (persons != null && persons.isArray()) {
                personRepository.init(Arrays.asList(mapper.treeToValue(persons, Person[].class)));
            }

            JsonNode firestations = node.get("firestations");
            if (firestations != null && firestations.isArray()) {
                firestationRepository.init(Arrays.asList(mapper.treeToValue(firestations, Firestation[].class)));
            }

            JsonNode medicalRecords = node.get("medicalrecords");
            if (medicalRecords != null && medicalRecords.isArray()) {
                medicalRecordRepository.init(Arrays.asList(mapper.treeToValue(medicalRecords, MedicalRecord[].class)));
            }
        };
    }

    static private <T> void edit(String key, List<T> value) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode nodes = (ObjectNode) mapper.readTree(readJSON());

        if (nodes.has(key)) {
            nodes.set(key, mapper.convertValue(value, JsonNode.class));
        } else {
            System.err.println("Key " + key + " not found");
            return;
        }

        FileWriter file = new FileWriter(json_path);
        BufferedWriter writer = new BufferedWriter(file);
        writer.write(nodes.toPrettyString());
        writer.close();
        file.close();
    }

    static public void editPersons(List<Person> value) throws IOException {
        edit("persons", value);
    }

    static public void editFirestations(List<Firestation> value) throws IOException {
        edit("firestations", value);
    }

    static public void editMedicalRecords(List<MedicalRecord> value) throws IOException {
        edit("medicalrecords", value);
    }
}
