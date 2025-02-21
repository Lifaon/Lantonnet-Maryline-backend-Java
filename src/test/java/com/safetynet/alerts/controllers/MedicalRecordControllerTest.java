package com.safetynet.alerts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.models.MedicalRecord;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.services.MedicalRecordService;
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
@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @MockitoBean
    private MedicalRecordService _medicalRecordService;

    @Autowired
    private MockMvc mvc;

    final private String uri = "/medicalRecord";

    final private MedicalRecord _record = new MedicalRecord();

    final private ObjectMapper _mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        _record.setFirstName("John");
        _record.setLastName("Smith");
        _record.setBirthdate("01/01/2000");
    }

    @Test
    public void testGetMethodNotAllowed() throws Exception {
        mvc.perform(get(uri)).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testCreateMedicalRecord() throws Exception {
        mvc.perform(post(uri)
                .contentType("application/json")
                .content(_mapper.writeValueAsString(_record))
        ).andExpect(status().isOk());
    }

    @Test
    public void testCreateMedicalRecordAlreadyExists() throws Exception {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                .doNothing().when(_medicalRecordService).create(any(MedicalRecord.class));

        mvc.perform(post(uri)
                .contentType("application/json")
                .content(_mapper.writeValueAsString(_record))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateMedicalRecordNoParam() throws Exception {
        mvc.perform(post(uri)).andExpect(status().isBadRequest());
    }

    @Test
    public void testEditMedicalRecord() throws Exception {
        mvc.perform(put(uri)
                .contentType("application/json")
                .content(_mapper.writeValueAsString(_record))
        ).andExpect(status().isOk());
    }

    @Test
    public void testEditMedicalRecordNotPresent() throws Exception {

        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                .doNothing().when(_medicalRecordService).edit(any(MedicalRecord.class));

        mvc.perform(put(uri)
                .contentType("application/json")
                .content(_mapper.writeValueAsString(_record))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testEditMedicalRecordNoParam() throws Exception {
        mvc.perform(put(uri)).andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        mvc.perform(delete(uri)
                .queryParam("firstName", "John")
                .queryParam("lastName", "Smith")
        ).andExpect(status().isOk());
    }

    @Test
    public void testDeleteMedicalRecordNotPresent() throws Exception {

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .doNothing().when(_medicalRecordService).delete(any(PersonName.class));

        mvc.perform(delete(uri)
                .queryParam("firstName", "John")
                .queryParam("lastName", "Smith")
        ).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteMedicalRecordNoParam() throws Exception {
        mvc.perform(delete(uri)).andExpect(status().isBadRequest());
    }

}
