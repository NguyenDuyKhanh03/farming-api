package com.example.Farming_App.controllers;

import com.example.Farming_App.constants.ProductsConstants;
import com.example.Farming_App.dto.ResponseDto;
import com.example.Farming_App.request.order.PostOrderRequest;
import com.example.Farming_App.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create-order-ghn")
    public ResponseEntity<ResponseDto> createOrderWithGHN(@RequestBody PostOrderRequest postOrderRequest){
        boolean isOrdered=orderService.createOrder(postOrderRequest);
        if (isOrdered) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductsConstants.STATUS_200,ProductsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        }
    }
}
