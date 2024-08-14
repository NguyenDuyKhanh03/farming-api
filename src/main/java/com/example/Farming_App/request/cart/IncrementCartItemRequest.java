package com.example.Farming_App.request.cart;

import lombok.Data;

@Data
public class IncrementCartItemRequest {
    private Long cartItemId;
    private int quantity;
}
