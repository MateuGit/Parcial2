package com.example.Parcial2.Component;

import com.example.Parcial2.exceptions.ApiErrorException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
public class CallComponent {

    public static String getCalls = "http://localhost:8080/callsManagement";
    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {
        restTemplate = new RestTemplateBuilder().build();
    }

    public ResponseEntity<List> getCallsByDate(LocalDate date) {
        String uri = getCalls + "?date=" + date.toString();
        ResponseEntity<List> list;
        try {
            list = restTemplate.getForEntity(uri, List.class);
        } catch (RuntimeException ex) {
            throw new ApiErrorException(((HttpClientErrorException) ex).getRawStatusCode());
        }
        return list;
    }
}
