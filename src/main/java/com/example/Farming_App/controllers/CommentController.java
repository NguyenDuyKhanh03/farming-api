package com.example.Farming_App.controllers;

import com.example.Farming_App.constants.ProductsConstants;
import com.example.Farming_App.dto.ResponseDto;
import com.example.Farming_App.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addComment(@RequestParam Long postId, @RequestParam String content) {
        boolean result = commentService.addComment(postId, content);
        if (result) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(ProductsConstants.STATUS_201,ProductsConstants.MESSAGE_201));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable Long commentId) {
        boolean result = commentService.deleteComment(commentId);
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
