package com.example.Parcial2.Controller;

import com.example.Parcial2.domains.response.ErrorResponseDTO;
import com.example.Parcial2.exceptions.ApiErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiErrorException.class)
    public ErrorResponseDTO handleApiErrorException(ApiErrorException ex) {
        return new ErrorResponseDTO(1, ex.getApiCode(), ex.getMessage());
    }
}
