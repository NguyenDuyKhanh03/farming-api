package com.example.Farming_App.controllers;

import com.example.Farming_App.constants.ProductsConstants;
import com.example.Farming_App.dto.ResponseDto;
import com.example.Farming_App.request.cart.AddToCartRequest;
import com.example.Farming_App.request.cart.DecrementCartItemRequest;
import com.example.Farming_App.request.cart.IncrementCartItemRequest;
import com.example.Farming_App.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addToCart(@RequestBody AddToCartRequest addToCartRequest){
        boolean isAdded= cartService.addToCart(addToCartRequest.getProductId(),addToCartRequest.getQuantity());

        if (isAdded) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductsConstants.STATUS_200,ProductsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        }
    }

    @PostMapping("/increment")
    public ResponseEntity<ResponseDto> increaseCartItem(@RequestBody IncrementCartItemRequest incrementCartItemRequest){
        boolean isIncreased= cartService.incrementCartItem(incrementCartItemRequest.getCartItemId(),
                incrementCartItemRequest.getQuantity());

        if (isIncreased) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductsConstants.STATUS_200,ProductsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        }
    }
    @PostMapping("/decrement")
    public ResponseEntity<ResponseDto> decreaseCartItem(@RequestBody DecrementCartItemRequest decrementCartItemRequest){
        boolean isDecreased= cartService.decrementCartItem(decrementCartItemRequest.getCartItemId(),
                decrementCartItemRequest.getQuantity());

        if (isDecreased) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductsConstants.STATUS_200,ProductsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<ResponseDto> removeFromCart(@RequestParam Long cartItemId){
        boolean isRemoved=cartService.removeFromCart(cartItemId);
        if (isRemoved) {
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
