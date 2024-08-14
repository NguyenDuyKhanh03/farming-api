package com.example.Farming_App.services;

import com.example.Farming_App.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AccountService {

    UserDetailsService userDetailsService();
    Optional<Account> getAccount();
    Account saveUser(Account user);
}
