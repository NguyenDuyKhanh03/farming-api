package com.example.Farming_App.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InputOutputException extends IOException {
    public InputOutputException(String message) {
        super(message);
    }
}
