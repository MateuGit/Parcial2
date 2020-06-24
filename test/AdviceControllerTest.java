package com.example.Parcial2;

import com.example.Parcial2.Component.CallComponent;
import com.example.Parcial2.Controller.AdviceController;
import com.example.Parcial2.Controller.CallController;
import com.example.Parcial2.Utils.ObjectConverter;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdviceControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private CallController callController;
    @Mock
    private CallComponent callComponent;

    public AdviceControllerTest() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(callController)
                .setControllerAdvice(new AdviceController())
                .build();
    }


    @Test
    public void ApiErrorException() throws Exception {
        LocalDate date = LocalDate.now();
        String requestJson = ObjectConverter.converterIntoJson(date);
        ResponseEntity responseEntity=new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        when(callComponent.getCallsByDate(date)).thenReturn(responseEntity);
        mockMvc.perform(get("/calls")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }



}
