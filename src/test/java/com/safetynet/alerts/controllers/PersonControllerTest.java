package com.safetynet.alerts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.models.Person;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @MockitoBean
    private PersonService _personService;

    @Autowired
    private MockMvc mvc;

    final private String uri = "/person";

    final private Person _person = new Person();

    final private ObjectMapper _mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        _person.setFirstName("John");
        _person.setLastName("Smith");
        _person.setAddress("Toto");
        _person.setCity("London");
        _person.setZip("12345");
        _person.setEmail("john.smith@gmail.com");
        _person.setPhone("0606060606");
    }

    @Test
    public void testGetMethodNotAllowed() throws Exception {
        mvc.perform(get(uri)).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testCreatePerson() throws Exception {
        mvc.perform(post(uri)
            .contentType("application/json")
            .content(_mapper.writeValueAsString(_person))
        ).andExpect(status().isOk());
    }

    @Test
    public void testCreatePersonAlreadyExists() throws Exception {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                .doNothing().when(_personService).create(any(Person.class));

        mvc.perform(post(uri)
                .contentType("application/json")
                .content(_mapper.writeValueAsString(_person))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testCreatePersonNoParam() throws Exception {
        mvc.perform(post(uri)).andExpect(status().isBadRequest());
    }

    @Test
    public void testEditPerson() throws Exception {
        mvc.perform(put(uri)
            .contentType("application/json")
            .content(_mapper.writeValueAsString(_person))
        ).andExpect(status().isOk());
    }

    @Test
    public void testEditPersonNotPresent() throws Exception {

        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
            .doNothing().when(_personService).edit(any(Person.class));

        mvc.perform(put(uri)
            .contentType("application/json")
            .content(_mapper.writeValueAsString(_person))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testEditPersonNoParam() throws Exception {
        mvc.perform(put(uri)).andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePerson() throws Exception {
        mvc.perform(delete(uri)
            .queryParam("firstName", "John")
            .queryParam("lastName", "Smith")
        ).andExpect(status().isOk());
    }

    @Test
    public void testDeletePersonNotPresent() throws Exception {

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .doNothing().when(_personService).delete(any(PersonName.class));

        mvc.perform(delete(uri)
            .queryParam("firstName", "John")
            .queryParam("lastName", "Smith")
        ).andExpect(status().isNoContent());
    }

    @Test
    public void testDeletePersonNoParam() throws Exception {
        mvc.perform(delete(uri)).andExpect(status().isBadRequest());
    }

}
