package com.example.Farming_App.request.order;

import lombok.Data;

@Data
public class Item {
    private String name;
    private int quantity;
    private int weight;
}
