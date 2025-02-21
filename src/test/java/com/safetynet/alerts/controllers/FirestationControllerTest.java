package com.safetynet.alerts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.models.Firestation;
import com.safetynet.alerts.models.miscellaneous.CoveredPeople;
import com.safetynet.alerts.models.miscellaneous.PersonInfo;
import com.safetynet.alerts.services.FirestationService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(FirestationController.class)
public class FirestationControllerTest {

    @MockitoBean
    private FirestationService _firestationService;

    @Autowired
    private MockMvc mvc;

    final private String uri = "/firestation";

    final private Firestation _record = new Firestation();

    final private ObjectMapper _mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        _record.setStation("1");
        _record.setAddress("Toto");
    }

    @Test
    public void testGetCoveredPeople() throws Exception {

        CoveredPeople people = new CoveredPeople();
        people.getPeople().add(new PersonInfo());
        people.addChild();

        Mockito.when(_firestationService.getCoveredPeople(any(String.class)))
            .thenReturn(people);

        mvc.perform(get(uri)
            .queryParam("stationNumber", "1"))
            .andExpect(status().isOk())
            .andExpect(content().json(_mapper.writeValueAsString(people)));
    }

    @Test
    public void testCreateFirestation() throws Exception {
        mvc.perform(post(uri)
                .contentType("application/json")
                .content(_mapper.writeValueAsString(_record))
        ).andExpect(status().isOk());
    }

    @Test
    public void testCreateFirestationAlreadyExists() throws Exception {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                .doNothing().when(_firestationService).create(any(Firestation.class));

        mvc.perform(post(uri)
                .contentType("application/json")
                .content(_mapper.writeValueAsString(_record))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFirestationNoParam() throws Exception {
        mvc.perform(post(uri)).andExpect(status().isBadRequest());
    }

    @Test
    public void testEditFirestation() throws Exception {
        mvc.perform(put(uri)
                .contentType("application/json")
                .content(_mapper.writeValueAsString(_record))
        ).andExpect(status().isOk());
    }

    @Test
    public void testEditFirestationNotPresent() throws Exception {

        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                .doNothing().when(_firestationService).edit(any(Firestation.class));

        mvc.perform(put(uri)
                .contentType("application/json")
                .content(_mapper.writeValueAsString(_record))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testEditFirestationNoParam() throws Exception {
        mvc.perform(put(uri)).andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteFirestation() throws Exception {
        mvc.perform(delete(uri)
            .queryParam("address", "Toto")
        ).andExpect(status().isOk());
    }

    @Test
    public void testDeleteFirestationsByNumber() throws Exception {
        mvc.perform(delete(uri)
            .queryParam("stationNumber", "1")
        ).andExpect(status().isOk());
    }

    @Test
    public void testDeleteFirestationNotPresent() throws Exception {

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .doNothing().when(_firestationService).delete(any(String.class));

        mvc.perform(delete(uri)
            .queryParam("address", "Toto")
        ).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteFirestationsByNumberNotPresent() throws Exception {

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .doNothing().when(_firestationService).deleteByNumber(any(String.class));

        mvc.perform(delete(uri)
            .queryParam("stationNumber", "1")
        ).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteFirestationNoParam() throws Exception {
        mvc.perform(delete(uri)).andExpect(status().isBadRequest());
    }

}
