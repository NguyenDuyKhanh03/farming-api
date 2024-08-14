package com.example.Farming_App.repositories;

import com.example.Farming_App.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByMail(String mail);
    Account findByRoleName(String name);
}
