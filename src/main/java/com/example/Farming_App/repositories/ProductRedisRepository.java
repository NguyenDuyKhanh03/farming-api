package com.example.Farming_App.repositories;

import com.example.Farming_App.dto.product.ProductDto;

import java.util.List;

public interface ProductRedisRepository {
    void saveProduct(ProductDto product);
    List<String> findAllProductsBySeller(Long sellerId);
}
