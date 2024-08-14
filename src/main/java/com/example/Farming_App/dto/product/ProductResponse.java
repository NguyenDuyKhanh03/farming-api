package com.example.Farming_App.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse extends ProductDto{
    private List<String> image;
}
