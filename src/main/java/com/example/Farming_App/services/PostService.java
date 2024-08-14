package com.example.Farming_App.services;

import com.example.Farming_App.dto.PostDto;
import com.example.Farming_App.entity.Post;

public interface PostService {
    boolean createPost(PostDto postDto);
    Post getPost(Long postId);
    boolean deletePost(Long postId);
}
