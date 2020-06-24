package com.example.Parcial2;

import com.example.Parcial2.Component.CallComponent;
import com.example.Parcial2.Controller.AdviceController;
import com.example.Parcial2.Controller.CallController;
import com.example.Parcial2.Utils.ObjectConverter;
import com.example.Parcial2.domains.response.CallsWithNameAndLastname;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CallControllerTest {
    MockMvc mockMvc;
    @InjectMocks
    private CallController callController;

    @Mock
    private CallComponent callComponent;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(callComponent)
                .setControllerAdvice(new AdviceController())
                .build();
    }


    @Test
    public void getByDateNoContent() throws Exception {
        LocalDate date=LocalDate.now();
        String requestJson= ObjectConverter.converterIntoJson(date);
        ResponseEntity responseEntity=new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        when(callComponent.getCallsByDate(date)).thenReturn(responseEntity);
        mockMvc.perform(get("/calls?date=2020-06-24")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void getByUsedOk() throws Exception {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        CallsWithNameAndLastname respo1 = factory.createProjection(CallsWithNameAndLastname.class);
        CallsWithNameAndLastname respo2 = factory.createProjection(CallsWithNameAndLastname.class);
        respo1.setLastname("German");
        respo1.setName("Saca el monitor");
        respo1.setQuantity(1);
        respo2.setLastname("Facu");
        respo2.setName("Sacate las projections");
        respo2.setQuantity(12313);

        List calls=new ArrayList<>();
        calls.add(respo1);
        calls.add(respo2);

        LocalDate date=LocalDate.now();

        ResponseEntity responseEntity=new ResponseEntity<>(calls, HttpStatus.OK);
        when(callComponent.getCallsByDate(date)).thenReturn(responseEntity);
        MvcResult result = mockMvc.perform(get("/calls?date=2020-06-24")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List u = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
        assertEquals(u.size(), calls.size());
    }
}
