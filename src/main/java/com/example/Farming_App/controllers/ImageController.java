package com.example.Farming_App.controllers;

import com.example.Farming_App.services.ImageService;
import com.example.Farming_App.services.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class ImageController {
    private final ImageServiceImpl imageService;

    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        return imageService.upload(multipartFile);
    }

}
