package com.example.Farming_App.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEs {
    private String name;
    private double price;
    private String description;
    private String categoryName;
    private double quantity;
    private double soldQuantity;
    private List<String> urls;
}
