package com.example.Farming_App.mapper.impl;

import com.example.Farming_App.mapper.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonMapper<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String mapTo(T o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Xử lý exception theo ý của bạn
            return null;
        }
    }


    public T mapFrom(String json,Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Xử lý exception theo ý của bạn
            return null;
        }
    }
}
