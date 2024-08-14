package com.example.Farming_App.services;

import com.example.Farming_App.entity.Account;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JWTService {
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateToken(UserDetails userDetails);

    String generateRefreshToken(HashMap<String, Object> objectObjectHashMap, UserDetails userDetails);
}
