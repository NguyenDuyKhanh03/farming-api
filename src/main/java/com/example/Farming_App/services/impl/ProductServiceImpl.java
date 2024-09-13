package com.example.Farming_App.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import com.example.Farming_App.dto.product.ProductDto;
import com.example.Farming_App.dto.product.ProductEs;
import com.example.Farming_App.dto.product.ProductRequest;
import com.example.Farming_App.dto.product.ProductResponse;
import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Category;
import com.example.Farming_App.entity.Image;
import com.example.Farming_App.entity.Product;
import com.example.Farming_App.exception.InputOutputException;
import com.example.Farming_App.exception.ResourceNotFoundException;
import com.example.Farming_App.mapper.Mapper;
import com.example.Farming_App.mapper.impl.JsonMapper;
import com.example.Farming_App.repositories.*;
import com.example.Farming_App.services.AccountService;
import com.example.Farming_App.services.ImageService;
import com.example.Farming_App.services.JWTService;
import com.example.Farming_App.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final Mapper<Product,ProductResponse> mapper;
    private final JWTService jwtService;
    private final ImageService imageService;
    private final CategoryRepository categoryRepository;
    private final AccountService accountService;
    private final ProductRedisRepository productRedisRepository;
    private final ElasticsearchClient client;

    private final JsonMapper<ProductResponse> productJsonMapper;
    public boolean addProduct(ProductRequest productRequest) throws InputOutputException {

        Optional<Account> account=accountService.getAccount();

        productRequest.setSoldQuantity(0);

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
//        product.setDiscount(productRequest.getDiscount());
        product.setDescription(productRequest.getDescription());
        // Load the category from the database
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category","id",String.valueOf(productRequest.getCategoryId())));
        product.setCategory(category);
        product.setQuantity(productRequest.getQuantity());
        product.setSoldQuantity(0);
        product.setSeller(account.get());


        Product savedProduct= productRepository.save(product);

        List<Image> images = new ArrayList<>();
        for (MultipartFile file : productRequest.getImages()) {
            Map<String,Object> cloudinaryImage= imageService.upload(file);
            String imageUrl = cloudinaryImage.get("secure_url").toString();
            Image image = new Image();
            image.setUrl(imageUrl);
            image.setName(savedProduct.getName());
            image.setRelationId(savedProduct.getId());
            image.setType("product");
            images.add(image);
            String publicId = cloudinaryImage.get("public_id").toString();
        }

        savedProduct.setImages(images);
        productRepository.save(savedProduct);

        ProductEs productEs=new ProductEs();
        productEs.setName(savedProduct.getName());
        productEs.setPrice(productRequest.getPrice());
        productEs.setDescription(savedProduct.getDescription());
        productEs.setCategoryName(savedProduct.getCategory().getName());
        productEs.setSoldQuantity(savedProduct.getSoldQuantity());
        productEs.setQuantity(savedProduct.getQuantity());
        productEs.setUrls(images.stream().map(Image::getUrl).collect(Collectors.toList()));

        try {
            // Tạo yêu cầu thêm tài liệu
            CreateResponse createResponse = client.create(e -> e
                    .index("products")
                    .id(String.valueOf(savedProduct.getId()))
                    .document(productEs)
            );

        } catch (IOException e) {
            // Ném ngoại lệ tùy chỉnh với thông báo chi tiết
            throw new InputOutputException("Failed to create document in Elasticsearch: " + e.getMessage());
        }
        return true;

    }

    @Override
    public List<ProductResponse> getListProduct() {
        Optional<Account> account=accountService.getAccount();
        List<String> s=productRedisRepository.findAllProductsBySeller(account.get().getId());
        if(s==null || s.isEmpty()) {
            List<Product> products=productRepository.findBySeller(account.get());
            List<ProductResponse> productResponses=products.stream()
                    .map(mapper::mapTo)
                    .collect(Collectors.toList());
            productResponses.forEach(productRedisRepository::saveProduct);
            return productResponses;
        }
        return s.stream()
                .map(this::convertJsonToProductDto)
                .collect(Collectors.toList());
    }


    @Override
    public int removeProductById(Long id) {

        Optional<Account> account=accountService.getAccount();
        productRepository.getProductByIdAndSeller(id,account.get())
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Product","id",String.valueOf(id))
                );
        return productRepository.removeProductById(id);
    }

    public List<ProductDto> searchProduct(String keyword){
        List<Product> products=productRepository.search(keyword)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Product","keyword",keyword)
                );
        if(products.isEmpty())
            throw  new ResourceNotFoundException("Product","keyword",keyword);
        return products.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    private ProductResponse convertJsonToProductDto(String productJson) {
        return productJsonMapper.mapFrom(productJson,ProductResponse.class);
    }


}
