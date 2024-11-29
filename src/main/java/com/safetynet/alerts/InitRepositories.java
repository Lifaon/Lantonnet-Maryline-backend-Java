package com.safetynet.alerts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.File;
import java.util.Scanner;

@Configuration
public class InitRepositories {

    private String json = "";

    @Bean("read")
    CommandLineRunner readJSON() {
        return args -> {
            File f = new File("./resources/data.json");
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                json += reader.nextLine();
            }
            reader.close();
            System.out.println("Read a file of size " + json.length());
        };
    }

    @Bean
    @DependsOn("read")
    CommandLineRunner initPersonRepository(PersonRepository repository) {
        return args -> {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            for (JsonNode node_person : node.get("persons")) {
                repository.createPerson(new Person(
                        node_person.get("firstName").asText(),
                        node_person.get("lastName").asText(),
                        node_person.get("address").asText(),
                        node_person.get("city").asText(),
                        node_person.get("zip").asText(),
                        node_person.get("phone").asText(),
                        node_person.get("email").asText()
                ));
            }
        };
    }
}
