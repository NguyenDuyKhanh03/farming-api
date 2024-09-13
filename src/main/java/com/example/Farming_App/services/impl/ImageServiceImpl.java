package com.example.Farming_App.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Farming_App.services.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Cloudinary cloudinary;

    @SuppressWarnings("unchecked")
    public Map<String,Object> upload(MultipartFile multipartFile){
        try {
            return (Map<String,Object>) this.cloudinary.uploader().upload(multipartFile.getBytes(),
                    ObjectUtils.asMap("resource_type","auto"));
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
