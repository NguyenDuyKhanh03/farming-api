package com.example.Farming_App.controllers;

import com.example.Farming_App.constants.ProductsConstants;
import com.example.Farming_App.dto.PostDto;
import com.example.Farming_App.dto.ResponseDto;
import com.example.Farming_App.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> createPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam("categoryId") Long categoryId,
            @RequestPart("images") List<MultipartFile> images)
    {
        PostDto postDto=new PostDto();
        postDto.setTitle(title);
        postDto.setContent(content);
        postDto.setCategoryId(categoryId);
        postDto.setImages(images);

        boolean isAdded= postService.createPost(postDto);

        if(isAdded)
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(ProductsConstants.STATUS_200,ProductsConstants.MESSAGE_200));
        else
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long postId) {
        boolean result = postService.deletePost(postId);
        if (result) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductsConstants.STATUS_200,ProductsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        }


    }
}
