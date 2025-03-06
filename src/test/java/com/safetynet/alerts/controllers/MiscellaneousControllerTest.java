package com.safetynet.alerts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.Utils;
import com.safetynet.alerts.models.miscellaneous.ChildAlert;
import com.safetynet.alerts.models.miscellaneous.ChildInfo;
import com.safetynet.alerts.models.miscellaneous.MedicalInfo;
import com.safetynet.alerts.models.miscellaneous.PersonEmergencyInfo;
import com.safetynet.alerts.models.miscellaneous.PersonMedicalInfo;
import com.safetynet.alerts.models.miscellaneous.PersonName;
import com.safetynet.alerts.services.MiscellaneousService;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MiscellaneousController.class)
public class MiscellaneousControllerTest {
    @MockitoBean
    private MiscellaneousService service;

    @Autowired
    private MockMvc mvc;

    final private ObjectMapper _mapper = new ObjectMapper();

    final private PersonEmergencyInfo personEmergencyInfo = new PersonEmergencyInfo();

    @BeforeEach
    void setUp() {
        personEmergencyInfo.setAge(25);
        personEmergencyInfo.setPhone("0606060606");
        personEmergencyInfo.setFirstName("John");
        personEmergencyInfo.setLastName("Smith");
        personEmergencyInfo.setAllergies(List.of("Peanuts"));
        personEmergencyInfo.setMedications(List.of("Estradiol"));
    }

    @Test
    public void getChildAlertTest() throws Exception {
        ChildAlert childAlert = new ChildAlert();
        childAlert.setAdults(List.of(new PersonName("John", "Smith")));
        childAlert.setChildren(List.of(new ChildInfo(new PersonName("Jean", "Dupont"), 12)));
        when(service.getChildAlert(any())).thenReturn(childAlert);
        mvc.perform(get("/childAlert")
            .queryParam("address", "Random Address"))
            .andExpect(status().isOk())
            .andExpect(content().json(_mapper.writeValueAsString(childAlert)));
    }

    @Test
    public void getPhoneAlertTest() throws Exception {
        List<String> phones = List.of("0606060606", "0707070707");
        when(service.getPhoneAlert(any())).thenReturn(phones);
        mvc.perform(get("/phoneAlert")
            .queryParam("firestation", "1"))
            .andExpect(status().isOk())
            .andExpect(content().json(_mapper.writeValueAsString(phones)));
    }

    @Test
    public void getFireInfoTest() throws Exception {
        var fireInfo = new Pair<>(
            "Random Address",
            List.of(personEmergencyInfo)
        );
        when(service.getFireInfo(any())).thenReturn(fireInfo);
        mvc.perform(get("/fire")
            .queryParam("address", "Random Address"))
            .andExpect(status().isOk())
            .andExpect(content().json(_mapper.writeValueAsString(fireInfo)));
    }

    @Test
    public void getFloodStationsTest() throws Exception {
        var floodStations = Map.of(
            "Random Address",
            List.of(personEmergencyInfo)
        );
        when(service.getFloodStations(any())).thenReturn(floodStations);
        mvc.perform(get("/flood/stations")
            .queryParam("stations", "1"))
            .andExpect(status().isOk())
            .andExpect(content().json(_mapper.writeValueAsString(floodStations)));
    }

    @Test
    public void getPeopleMedicalInfosTest() throws Exception {
        var personInfo = new PersonMedicalInfo();
        Utils.copyFields(MedicalInfo.class, personEmergencyInfo, personInfo);
        personInfo.setAddress("Random Address");
        personInfo.setAge(25);
        personInfo.setEmail("email@email.com");
        var peopleInfo = List.of(personInfo);
        when(service.getPeopleMedicalInfos(any())).thenReturn(peopleInfo);
        mvc.perform(get("/personInfolastName")
            .queryParam("lastname", "Smith"))
            .andExpect(status().isOk())
            .andExpect(content().json(_mapper.writeValueAsString(peopleInfo)));
    }

    @Test
    public void getCommunityEmailTest() throws Exception {
        var emails = Set.of("email@email.com");
        when(service.getCommunityEmail(any())).thenReturn(emails);
        mvc.perform(get("/communityEmail")
            .queryParam("city", "Totoronto"))
            .andExpect(status().isOk())
            .andExpect(content().json(_mapper.writeValueAsString(emails)));
    }

    @Test
    public void getUnknownUriTest() throws Exception {
        mvc.perform(get("/TotoTutuTata"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void getMissingParametersTest() throws Exception {
        mvc.perform(get("/fire"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getInternalErrorTest() throws Exception {
        when(service.getFireInfo(any())).thenThrow(new RuntimeException());
        mvc.perform(get("/fire")
            .queryParam("address", "toto"))
            .andExpect(status().isInternalServerError());
    }

}
