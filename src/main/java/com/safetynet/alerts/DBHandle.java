package com.safetynet.alerts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Scanner;

@Configuration()
@PropertySource("classpath:application.properties")
public class DBHandle {

    final private String _json_path;
    final private ObjectMapper _mapper = new ObjectMapper();
    private ObjectNode _nodes;

    final private Logger LOGGER = LogManager.getLogger();

    DBHandle(
        @Value("${com.safetynet.alerts.DBHandle.Path}") String path,
        @Value("${com.safetynet.alerts.DBHandle.OGPath}") String og_path
    ) {
        _json_path = path;
        try {
            File f = new File(_json_path);
            if (!f.isFile()) {
                File og = new File(og_path);
                Files.copy(og.toPath(), f.toPath());
                LOGGER.debug("Copied '{}' to '{}'", og, f);
            }
            StringBuilder json_data = new StringBuilder();
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                json_data.append(reader.nextLine());
            }
            reader.close();
            _nodes = (ObjectNode) _mapper.readTree(json_data.toString());
            LOGGER.debug("Initialized {} nodes", _nodes.size());
        } catch (Exception e) {
            LOGGER.error(e);
            System.exit(1);
        }
    }

    public <T> Optional<T> get(String key, Class<T> type) {
        try {
            return Optional.ofNullable(_mapper.treeToValue(_nodes.get(key), type));
        }
        catch (Exception e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

    public <T> void set(String key, T value) {

        if (_nodes.has(key)) {
            _nodes.set(key, _mapper.convertValue(value, JsonNode.class));
        } else {
            LOGGER.error("Key '{}' not found in database", key);
            return;
        }

        try {
            FileWriter file = new FileWriter(_json_path);
            BufferedWriter writer = new BufferedWriter(file);
            writer.write(_nodes.toPrettyString());
            writer.close();
            file.close();
            LOGGER.debug("Edited '{}' in database", key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
