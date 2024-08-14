package com.example.Farming_App.services;

import com.example.Farming_App.entity.Cart;

public interface CartService  {
    boolean addToCart(Long productId,int quantity);
    Cart calculatePrice(Cart cart);
    boolean incrementCartItem(Long cartItemId, int quantity);
    boolean decrementCartItem(Long cartItemId, int quantity);
    boolean removeFromCart(Long cartItemId);

    void emptyCart();
}
