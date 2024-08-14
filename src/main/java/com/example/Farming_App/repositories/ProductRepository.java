package com.example.Farming_App.repositories;

import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findBySeller(Account account);
    @Transactional
    @Modifying
    int removeProductById(Long id);
    Optional<Product> getProductByIdAndSeller(Long id, Account seller);

    @Query("SELECT p FROM Product p WHERE CONCAT(p.name, ' ', p.category, ' ', p.description, ' ', p.price) LIKE %?1%")
    Optional<List<Product>> search(String keyword);


}
