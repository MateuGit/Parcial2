package com.example.Parcial2;

import com.example.Parcial2.Component.CallComponent;
import com.example.Parcial2.domains.response.CallsWithNameAndLastname;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CallComponentTest {

    @Mock
    private RestTemplate rt;

    private CallComponent callComponent;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        callComponent = new CallComponent();
    }

    @Test
    public void testGetCallByDate(){
        LocalDate date=LocalDate.now();
        String uri="http://localhost:8080/callsManagement?date=2020-06-24";//nefastos endpoints tenia
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        CallsWithNameAndLastname respo1 = factory.createProjection(CallsWithNameAndLastname.class);
        CallsWithNameAndLastname respo2 = factory.createProjection(CallsWithNameAndLastname.class);
        respo1.setLastname("German");
        respo1.setName("Saca el monitor");
        respo1.setQuantity(1);
        respo2.setLastname("Facu");
        respo2.setName("el jr");
        respo2.setQuantity(12313);

        List calls=new ArrayList<>();
        calls.add(respo1);
        calls.add(respo2);
        ResponseEntity responseEntity=new ResponseEntity<>(calls, HttpStatus.OK);

        when(rt.getForEntity(uri,List.class)).thenReturn(responseEntity);
        assertEquals(responseEntity, callComponent.getCallsByDate(LocalDate.of(2020,6,24)));
    }


}
