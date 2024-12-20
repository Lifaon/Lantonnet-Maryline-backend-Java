package com.safetynet.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.*;
import java.lang.*;

@Configuration()
public class DBHandle {

    final private String json_path = "./resources/data.json";
    final private ObjectMapper mapper = new ObjectMapper();
    private ObjectNode nodes;

    DBHandle() {
        try {
            File f = new File(json_path);
            if (!f.isFile()) {
                f = new File("./resources/og_data.json");
            }
            StringBuilder json_data = new StringBuilder();
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

    public <T> T get(String key, Class<T> type) throws JsonProcessingException {
        return mapper.treeToValue(nodes.get(key), type);
    }

    public <T> void set(String key, T value) throws IOException {

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
