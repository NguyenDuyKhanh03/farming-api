package com.example.Farming_App.services.impl;

import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Cart;
import com.example.Farming_App.entity.CartDetail;
import com.example.Farming_App.entity.Product;
import com.example.Farming_App.exception.InvalidArgumentException;
import com.example.Farming_App.exception.ResourceNotFoundException;
import com.example.Farming_App.repositories.CartRepository;
import com.example.Farming_App.repositories.ProductRepository;
import com.example.Farming_App.services.AccountService;
import com.example.Farming_App.services.CartService;
import com.example.Farming_App.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final AccountService accountService;
    private final ProductRepository productRepository;
    private final ProductService productService;


    @Transactional
    public boolean addToCart(Long productId,int quantity){
        Account user=accountService.getAccount()
                .orElseThrow(
                        ()-> new ResourceNotFoundException("User","token","...")
                );
        Cart cart=user.getCart();
        if(Objects.nonNull(cart) && Objects.nonNull(cart.getCartDetails()) && !cart.getCartDetails().isEmpty()){
            Optional<CartDetail> cartDetail=cart.getCartDetails()
                    .stream()
                    .filter(ci->ci.getProduct().getId().equals(productId))
                    .findAny();
            if (cartDetail.isPresent()){
                if(cartDetail.get().getProduct().getQuantity() < cartDetail.get().getQuantity()+quantity)
                    throw new InvalidArgumentException("Product","stock",String.valueOf(quantity));
                cartDetail.get().setQuantity(cartDetail.get().getQuantity()+quantity);
                Cart updatedCart=calculatePrice(cart);
                cart=cartRepository.save(updatedCart);
                return true;
            }
        }

        if(Objects.isNull(cart)){
            cart = createCart(user);
        }

        Product product= productRepository.findById(productId)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Product","id",String.valueOf(productId))
                );

        if(product.getQuantity()<quantity)
            throw new InvalidArgumentException("Product","stock",String.valueOf(quantity));

        CartDetail cartDetail= new CartDetail();
        cartDetail.setQuantity(quantity);
        cartDetail.setProduct(product);
        cartDetail.setCart(cart);

        if(Objects.isNull(cart.getCartDetails()))
            cart.setCartDetails(new ArrayList<>());

        cart.getCartDetails().add(cartDetail);
        cart=calculatePrice(cart);
        cart=cartRepository.save(cart);
        return true;
    }

    @Transactional
    public boolean incrementCartItem(Long cartItemId, int quantity){
        Account account =accountService.getAccount().get();
        Cart cart=account.getCart();

        if(Objects.isNull(cart) || Objects.isNull(cart.getCartDetails()) || cart.getCartDetails().isEmpty())
            throw new InvalidArgumentException("Cart","list cart item","...");

        CartDetail cartDetail=cart.getCartDetails()
                .stream()
                .filter(ci->ci.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Cart item","id",String.valueOf(cartItemId))
                );

        if(cartDetail.getProduct().getQuantity()< (cartDetail.getQuantity()+ quantity) ){
            throw new InvalidArgumentException("Product","quantity",String.valueOf(quantity));
        }
        cartDetail.setQuantity(cartDetail.getQuantity()+quantity);
        cart=calculatePrice(cart);
        cartRepository.save(cart);
        return true;
    }

    @Transactional
    public boolean decrementCartItem(Long cartItemId, int quantity){
        Account account =accountService.getAccount().get();
        Cart cart=account.getCart();

        if(Objects.isNull(cart) || Objects.isNull(cart.getCartDetails()) || cart.getCartDetails().isEmpty())
            throw new InvalidArgumentException("Cart","list cart item","...");

        CartDetail cartDetail=cart.getCartDetails()
                .stream()
                .filter(ci->ci.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Cart item","id",String.valueOf(cartItemId))
                );

        if(cartDetail.getQuantity()-quantity<=0){
            List<CartDetail> cartDetailList= cart.getCartDetails();
            cartDetailList.remove(cartDetail);
            cart.setCartDetails(cartDetailList);
            cart=calculatePrice(cart);
            cartRepository.save(cart);
            return true;
        }

        cartDetail.setQuantity(cartDetail.getQuantity()-quantity);
        cart=calculatePrice(cart);
        cartRepository.save(cart);
        return true;
    }

    public Cart calculatePrice(Cart cart){
        cart.setTotalPrice(0F);

        cart.getCartDetails().forEach(cartDetail -> {
            cart.setTotalPrice(cart.getTotalPrice() + (cartDetail.getQuantity()*cartDetail.getProduct().getPrice()));
        });
        return cart;
    }

    private Cart createCart(Account user) {
        Cart cart = new Cart();
        cart.setCustomer(user);
        return cart;
    }
    public void saveCart(Cart cart) {
        if (Objects.isNull(cart)) {
            throw new InvalidArgumentException("Cart","...","...");
        }
        cartRepository.save(cart);
    }

    public boolean removeFromCart(Long cartItemId){
        Account user=accountService.getAccount()
                .orElseThrow(
                        ()->new ResourceNotFoundException("User","token","***")
                );
        Cart cart=user.getCart();

        if(Objects.isNull(cart) || Objects.isNull(cart.getCartDetails()) && cart.getCartDetails().isEmpty()){
            throw new ResourceNotFoundException("Cart or cartitem","id",String.valueOf(cartItemId));
        }

        List<CartDetail> cartDetailList=cart.getCartDetails();

        CartDetail cartDetail=cart.getCartDetails()
                .stream()
                .filter(ci->ci.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(
                        ()->new ResourceNotFoundException("Cart item","id",String.valueOf(cartItemId))
                );
        cartDetailList.remove(cartDetail);

        cart.setCartDetails(cartDetailList);
        cart=calculatePrice(cart);
        cartRepository.save(cart);
        return true;
    }

    @Override
    public void emptyCart() {
        Account user=accountService.getAccount().get();
        user.setCart(null);
        accountService.saveUser(user);

    }

    public Cart getCart(){
        return accountService.getAccount().get().getCart();
    }
}
