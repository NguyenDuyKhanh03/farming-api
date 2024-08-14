package com.example.Farming_App.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s already exists with the given input data %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
