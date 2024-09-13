package com.example.Farming_App.controllers;

import com.example.Farming_App.constants.ProductsConstants;
import com.example.Farming_App.dto.product.ProductDto;
import com.example.Farming_App.dto.ResponseDto;
import com.example.Farming_App.dto.product.ProductRequest;
import com.example.Farming_App.dto.product.ProductResponse;
import com.example.Farming_App.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;


    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addProduct(@RequestParam("name") String name,
                                             @RequestParam("price") double price,
                                             @RequestParam("discount") int discount,
                                             @RequestParam("description") String description,
                                             @RequestParam("categoryId") Long categoryId,
                                             @RequestParam("quantity") int quantity,
                                             @RequestPart("images") List<MultipartFile> images) throws IOException {

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(name);
        productRequest.setPrice(price);
//        productRequest.setDiscount(discount);
        productRequest.setDescription(description);
        productRequest.setCategoryId(categoryId);
        productRequest.setQuantity(quantity);
        productRequest.setImages(images);

        boolean isAdded = productService.addProduct(productRequest);
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

    @GetMapping("/list-product")
    public ResponseEntity<?> getListProductFromUser(){
        List<ProductResponse> productResponses=productService.getListProduct();
        if(productResponses.isEmpty() || Objects.isNull(productResponses))
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponses);
    }

    @DeleteMapping("/remove-product/{id}")
    public ResponseEntity<ResponseDto> removeProduct(@PathVariable("id") Long id){
        int isDeleted=productService.removeProductById(id);
        if(isDeleted==1)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductsConstants.STATUS_200,ProductsConstants.MESSAGE_200));
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        }

    }

    @GetMapping("/hello")
    public String abc(){
        return "hello";
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam String keyword){
        List<ProductDto> productDtos=productService.searchProduct(keyword);
        if(productDtos!=null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(productDtos);
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        }
    }

}
