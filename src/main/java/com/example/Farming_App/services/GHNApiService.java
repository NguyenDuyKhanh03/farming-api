package com.example.Farming_App.services;

import com.example.Farming_App.request.order.PostOrderRequest;
import com.example.Farming_App.request.order.ShippingOrderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GHNApiService {
    @Value("${ghn.api.url}")
    private String ghnApiUrl;

    @Value("${ghn.api.token}")
    private String ghnApiToken;

    @Value("${ghn.api.shopid}")
    private String ghnApiShopId;

    public Mono<ShippingOrderResponse> createShippingOrder(PostOrderRequest postOrderRequest) {
        WebClient webClient = WebClient.create(ghnApiUrl);

        return webClient.post()
                .uri("/shiip/public-api/v2/shipping-order/create")
                .header("token", ghnApiToken)
                .header("shopid",ghnApiShopId)
                .body(BodyInserters.fromValue(postOrderRequest))
                .retrieve()
                .bodyToMono(ShippingOrderResponse.class);
    }
}
