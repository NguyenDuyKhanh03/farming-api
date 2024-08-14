package com.example.Farming_App.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataPrice {
    private String name;
    private String unit;
    private String price;
}
