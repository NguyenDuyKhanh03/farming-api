package com.example.Farming_App.request.cart;

import lombok.Data;

@Data
public class DecrementCartItemRequest {
    private Long cartItemId;
    private int quantity;
}
