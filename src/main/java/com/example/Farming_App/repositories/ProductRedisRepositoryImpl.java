package com.example.Farming_App.repositories;

import com.example.Farming_App.dto.product.ProductDto;
import com.example.Farming_App.mapper.impl.JsonMapper;
import com.example.Farming_App.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class ProductRedisRepositoryImpl implements ProductRedisRepository {
    private static final String REDIS_ENTITY_LIST_PREFIX = "FarmersMarket:ProductList:";
    private static final String REDIS_ENTITY_PREFIX = "FarmersMarket:Product:";

    private final RedisTemplate<String, String> redisTemplate;
    private ListOperations<String, String> listOperations;
    private final AccountService accountService;

    private final JsonMapper<ProductDto> productJsonMapper;


    @PostConstruct
    private void init() {
        listOperations = redisTemplate.opsForList();
    }

    private String generateListKey(Long sellerId) {
        return String.format("%s%d", REDIS_ENTITY_LIST_PREFIX, sellerId);
    }

    private String generateProductKey(Long productId) {
        return String.format("%s%d", REDIS_ENTITY_PREFIX, productId);
    }

    public void saveProduct(ProductDto product) {
        String s= mapToAndConvertToJson(product);
        String listKey=generateListKey(accountService.getAccount().get().getId());
        listOperations.rightPush(listKey, s);
        redisTemplate.expire(listKey,12*60*60, TimeUnit.SECONDS);
    }

    public List<String> findAllProductsBySeller(Long sellerId) {
        return listOperations.range(generateListKey(sellerId), 0, -1);
    }

    private String mapToAndConvertToJson(ProductDto product) {
        // Chuyển đổi ProductDto thành JSON
        String productJson = productJsonMapper.mapTo(product);
        System.out.println("JSON của ProductDto: " + productJson); // In JSON để kiểm tra
        return productJson;
    }
}
