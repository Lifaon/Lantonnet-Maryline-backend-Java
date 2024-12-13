package com.safetynet.alerts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.*;

@Configuration()
public class DBHandle {

    final private String json_path = "./resources/data.json";
    final private ObjectMapper mapper = new ObjectMapper();
    private ObjectNode nodes;

    DBHandle() {
        try {
            StringBuilder json_data = new StringBuilder();
            File f = new File(json_path);
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                json_data.append(reader.nextLine());
            }
            reader.close();
            nodes = (ObjectNode) mapper.readTree(json_data.toString());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public JsonNode getNode(String key) {
        return nodes.get(key);
    }

    public <T> void editNode(String key, T value) throws IOException {

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

}
