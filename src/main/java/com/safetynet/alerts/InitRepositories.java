package com.safetynet.alerts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

@Configuration
public class InitRepositories {

    @Bean
    CommandLineRunner initPersonRepository(PersonRepository repository) {
        return args -> {

            File f = new File("./resources/data.json");
            Scanner reader = new Scanner(f);
            String json_string = "";
            while (reader.hasNextLine()) {
                json_string += reader.nextLine();
            }
            reader.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json_string);

            JsonNode persons = node.get("persons");
            if (persons != null && persons.isArray()) {
                repository.init(Arrays.asList(mapper.treeToValue(persons, Person[].class)));
            }
        };
    }
}
