package com.example.Farming_App.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException{
    public InvalidArgumentException(String fieldName){
        super(String.format("%s is not valid",fieldName));
    }
}
