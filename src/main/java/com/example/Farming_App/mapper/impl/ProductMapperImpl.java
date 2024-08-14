package com.example.Farming_App.mapper.impl;

import com.example.Farming_App.dto.product.ProductDto;
import com.example.Farming_App.dto.product.ProductResponse;
import com.example.Farming_App.entity.Image;
import com.example.Farming_App.entity.Product;
import com.example.Farming_App.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements Mapper<Product, ProductResponse> {
    private final ModelMapper modelMapper;

    @Override
    public ProductResponse mapTo(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setDiscount(product.getDiscount());
        productResponse.setDescription(product.getDescription());
        productResponse.setCategoryId(product.getCategory().getId());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setSoldQuantity(product.getSoldQuantity());
//        productDto.setSeller(product.getSeller());

        List<String> urls=new ArrayList<>();
        for(Image image:product.getImages()){
            urls.add(image.getUrl());
        }
        productResponse.setImage(urls);
        return productResponse;
    }

    @Override
    public Product mapFrom(ProductResponse productResponse) {
        return modelMapper.map(productResponse,Product.class);
    }
}
