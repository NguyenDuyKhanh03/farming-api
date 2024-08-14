package com.example.Farming_App.dto.product;

import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Category;
import com.example.Farming_App.entity.Image;
import com.example.Farming_App.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(
        name = "Product",
        description = "Schema to hold product information"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"images"})
public class ProductDto {
    private Long id;

    @NotEmpty(message = "Name can not be a null or empty")
    private String name;

    @NotEmpty(message = "Price can not be a null or empty")
    private double price;

    private int discount;
    private String description;

    @NotEmpty(message = "Category id can not be a null or empty")
    private Long categoryId;

    @NotEmpty(message = "Quantity can not be a null or empty")
    private double quantity;

    private double soldQuantity;

//    @NotEmpty(message = "Seller can not be a null or empty")
//    private Account seller;

//    private List<MultipartFile> images;
//    private List<String> image;
}
