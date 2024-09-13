package com.example.Farming_App.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageService {
    Map<String,Object> upload(MultipartFile multipartFile);
}
