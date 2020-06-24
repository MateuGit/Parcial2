package com.example.Parcial2.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiErrorException extends RuntimeException {
    private Integer apiCode;

    public ApiErrorException(Integer apiCode) {
        super("An error has ocurred with the api connection.");
        this.apiCode = apiCode;
    }
}
