package com.example.Farming_App.repositories;

import com.example.Farming_App.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    void deleteById(Long postId);
}
