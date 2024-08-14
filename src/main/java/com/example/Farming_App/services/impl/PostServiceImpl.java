package com.example.Farming_App.services.impl;

import com.example.Farming_App.dto.PostDto;
import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Category;
import com.example.Farming_App.entity.Image;
import com.example.Farming_App.entity.Post;
import com.example.Farming_App.exception.ResourceNotFoundException;
import com.example.Farming_App.repositories.AccountRepository;
import com.example.Farming_App.repositories.CategoryRepository;
import com.example.Farming_App.repositories.PostRepository;
import com.example.Farming_App.services.ImageService;
import com.example.Farming_App.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    public boolean createPost(PostDto postDto){
        Optional<Account> account=getAccount();
        if(account.isEmpty())
            throw new ResourceNotFoundException("Account","email",account.get().getUsername());

        Post post=new Post();
        post.setAuthors(account.get());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setStatus(1);
        Category category=categoryRepository.findById(postDto.getCategoryId())
                        .orElseThrow(
                                ()-> new ResourceNotFoundException("Category","id",String.valueOf(postDto.getCategoryId()))
                        );
        post.setCategories(category);

        Post savesPost=postRepository.save(post);

        List<Image> images=new ArrayList<>();
        for (MultipartFile file:postDto.getImages()){
            String imageUrl=imageService.upload(file);
            Image image=new Image();
            image.setName(post.getTitle());
            image.setUrl(imageUrl);
            image.setType("post");
            image.setRelationId(savesPost.getId());
            images.add(image);
        }
        savesPost.setImages(images);

        return postRepository.save(savesPost)!=null;
    }

    public boolean deletePost(Long postId){
        postRepository.findById(postId)
                .orElseThrow(
                        ()->new ResourceNotFoundException("Post","id",String.valueOf(postId))
                );
        postRepository.deleteById(postId);
        return true;

    }

    public Post getPost(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(
                        ()->new ResourceNotFoundException("Post","id",String.valueOf(postId))

        );
    }

    private Optional<Account> getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username ="";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return accountRepository.findByMail(username);
    }
}
