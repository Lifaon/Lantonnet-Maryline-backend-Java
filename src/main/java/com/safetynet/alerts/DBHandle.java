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

    final private String _json_path = "./resources/data.json";
    final private ObjectMapper _mapper = new ObjectMapper();
    private ObjectNode _nodes;

    DBHandle() {
        try {
            File f = new File(_json_path);
            if (!f.isFile()) {
                f = new File("./resources/og_data.json");
            }
            StringBuilder json_data = new StringBuilder();
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                json_data.append(reader.nextLine());
            }
            reader.close();
            _nodes = (ObjectNode) _mapper.readTree(json_data.toString());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public <T> T get(String key, Class<T> type) throws JsonProcessingException {
        return _mapper.treeToValue(_nodes.get(key), type);
    }

    public <T> void set(String key, T value) throws IOException {

        if (_nodes.has(key)) {
            _nodes.set(key, _mapper.convertValue(value, JsonNode.class));
        } else {
            System.err.println("Key " + key + " not found");
            return;
        }

        FileWriter file = new FileWriter(_json_path);
        BufferedWriter writer = new BufferedWriter(file);
        writer.write(_nodes.toPrettyString());
        writer.close();
        file.close();
    }

}
