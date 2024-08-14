package com.example.Farming_App.services.impl;

import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Cart;
import com.example.Farming_App.entity.Order;
import com.example.Farming_App.entity.OrderDetail;
import com.example.Farming_App.exception.InvalidArgumentException;
import com.example.Farming_App.repositories.OrderRepository;
import com.example.Farming_App.request.order.PostOrderRequest;
import com.example.Farming_App.request.order.ShippingOrderResponse;
import com.example.Farming_App.services.AccountService;
import com.example.Farming_App.services.CartService;
import com.example.Farming_App.services.GHNApiService;
import com.example.Farming_App.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AccountService accountService;
    private final CartService cartService;
    private final GHNApiService ghnApiService;
    public boolean createOrder(PostOrderRequest postOrderRequest){
        Account user=accountService.getAccount().get();
        Cart cart=user.getCart();
        Order saveOrder = new Order();
        saveOrder.setOrderDate(new Date());
        saveOrder.setShipAddress(postOrderRequest.getFromAddress());
        saveOrder.setBillingAddress(postOrderRequest.getToAddress());
        saveOrder.setCustomer(user);
        saveOrder.setOrderDetailList(new ArrayList<>());

        cart.getCartDetails().forEach(cartItem->{
            cartItem.getProduct().setSoldQuantity(cartItem.getProduct().getSoldQuantity()+cartItem.getQuantity());
            cartItem.getProduct().setQuantity(cartItem.getProduct().getQuantity()-cartItem.getQuantity());
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setOrder(saveOrder);
            orderDetail.setProduct(cartItem.getProduct());
            saveOrder.getOrderDetailList().add(orderDetail);
        });

        saveOrder.setTotalPrice(cart.getTotalPrice());
        saveOrder.setState("picking");

        ShippingOrderResponse shippingOrderResponse=ghnApiService.createShippingOrder(postOrderRequest).block();

        if(shippingOrderResponse!=null){
            saveOrder.setTotalCargoPrice(shippingOrderResponse.getData().getTotalFee());
            saveOrder.setTotalAmount(saveOrder.getTotalPrice()+saveOrder.getTotalCargoPrice());
            orderRepository.save(saveOrder);
            cartService.emptyCart();
            return true;
        }
        else
            return false;


//         Sau khi lưu đơn hàng, gọi API GHN để tạo đơn hàng vận chuyển
//        ghnApiService.createShippingOrder(postOrderRequest)
//                .flatMap(shippingOrderResponse -> {
//                    // Lấy thông tin phí vận chuyển từ shippingOrderResponse và cập nhật vào đơn hàng
//                    saveOrder.setTotalCargoPrice(shippingOrderResponse.getData().getTotalFee());
//                    // Cập nhật đơn hàng trong cơ sở dữ liệu với thông tin phí vận chuyển
//                    return Mono.just(orderRepository.save(saveOrder));
//                })
//                .subscribe(); // Cần phải subscribe để kích hoạt thực thi
//
//        orderRepository.save(saveOrder);
//        cartService.emptyCart(); // Làm rỗng giỏ hàng sau khi tạo đơn hàng thành công
//        return true;
    }


}
