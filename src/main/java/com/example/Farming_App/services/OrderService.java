package com.example.Farming_App.services;

import com.example.Farming_App.request.order.PostOrderRequest;

public interface OrderService {
    boolean createOrder(PostOrderRequest postOrderRequest);
}
