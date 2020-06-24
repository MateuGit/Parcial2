package com.example.Parcial2.Controller;

import com.example.Parcial2.Component.CallComponent;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("calls")
@RequiredArgsConstructor
public class CallController {

    private final CallComponent callComponent;

    @ApiOperation(value = "View all the invoices for an specific user", response = List.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The list of Name + last name + calls quantity"),
            @ApiResponse(code = 204, message = "The list of Name + last name + calls quantity is empty")
    })
    @GetMapping
    public ResponseEntity<List> getCallsByDate(@ApiParam(value = "The specific date", required = false)
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathParam("date") Optional<LocalDate> date) {
        return callComponent.getCallsByDate(date.orElse(LocalDate.now()));
    }

}
