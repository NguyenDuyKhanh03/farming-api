package com.example.Farming_App.services;

import com.example.Farming_App.dto.JwtAuthenticationResponse;
import com.example.Farming_App.dto.SignInRequest;
import com.example.Farming_App.dto.SignUpRequest;
import com.example.Farming_App.entity.Account;

import java.security.NoSuchAlgorithmException;

public interface AuthenticationService {
    Account signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
    int resetPassword(String mail,String newPassword);
    JwtAuthenticationResponse signInGoogle(String email) throws NoSuchAlgorithmException;
}
