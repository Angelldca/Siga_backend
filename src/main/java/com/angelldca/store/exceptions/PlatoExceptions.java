package com.angelldca.store.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PlatoExceptions extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public PlatoExceptions(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
