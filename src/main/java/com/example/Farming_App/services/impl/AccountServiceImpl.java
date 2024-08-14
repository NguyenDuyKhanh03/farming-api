package com.example.Farming_App.services.impl;

import com.example.Farming_App.entity.Account;
import com.example.Farming_App.exception.InvalidArgumentException;
import com.example.Farming_App.exception.ResourceNotFoundException;
import com.example.Farming_App.repositories.AccountRepository;
import com.example.Farming_App.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;



    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return accountRepository.findByMail(username)
                        .orElseThrow(()->new ResourceNotFoundException("User","email",username));
            }
        };
    }

    public Account saveUser(Account user) {
        if (Objects.isNull(user)) {
            throw new InvalidArgumentException("Null user","..","..");
        }
        return accountRepository.save(user);
    }

    public Optional<Account> getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username ="";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return accountRepository.findByMail(username);
    }
}
