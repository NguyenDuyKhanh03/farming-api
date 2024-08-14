package com.example.Farming_App.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException{
    public InvalidArgumentException(String resourceName,String fieldName, String fieldValue){
        super(String.format("%s is not valid",resourceName,fieldName,fieldValue));
    }
    public InvalidArgumentException(String resourceName){
        super(String.format("%s does not have desired %s: '%s'",resourceName));
    }
    public InvalidArgumentException() {
        super("A product in your cart is out of stock.");
    }
}
