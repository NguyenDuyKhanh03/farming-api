package com.example.Farming_App.dto.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductRequest extends ProductDto {
    private List<MultipartFile> images;
}
