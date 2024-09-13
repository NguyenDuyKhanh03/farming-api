package com.example.Farming_App.services;

import com.example.Farming_App.dto.product.ProductDto;
import com.example.Farming_App.dto.product.ProductRequest;
import com.example.Farming_App.dto.product.ProductResponse;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    boolean addProduct(ProductRequest productRequest) throws IOException;

    List<ProductResponse> getListProduct();

    int removeProductById(Long id);
    List<ProductDto> searchProduct(String keyword);
}
