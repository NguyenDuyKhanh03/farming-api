package com.example.Farming_App.mapper.impl;

import com.example.Farming_App.dto.PostDto;
import com.example.Farming_App.entity.Image;
import com.example.Farming_App.entity.Post;
import com.example.Farming_App.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

public class PostMapperImpl implements Mapper<Post, PostDto> {
    @Override
    public PostDto mapTo(Post post) {
        PostDto postDto=new PostDto();
        postDto.setContent(post.getContent());

        List<String> urls=new ArrayList<>();
        for (Image s:post.getImages()){
            urls.add(s.getUrl());
        }

        postDto.setImage(urls);
        return postDto;
    }

    @Override
    public Post mapFrom(PostDto postDto) {
        Post post=new Post();
        post.setContent(post.getContent());
        post.setImages(post.getImages());
        post.setTitle(post.getTitle());
        return post;
    }
}
