package com.example.Farming_App.services.impl;

import com.example.Farming_App.dto.JwtAuthenticationResponse;
import com.example.Farming_App.dto.SignInRequest;
import com.example.Farming_App.dto.SignUpRequest;
import com.example.Farming_App.entity.Account;
import com.example.Farming_App.exception.AccountAlreadyExistsException;
import com.example.Farming_App.exception.ResourceNotFoundException;
import com.example.Farming_App.repositories.AccountRepository;
import com.example.Farming_App.repositories.RoleRepository;
import com.example.Farming_App.services.AuthenticationService;
import com.example.Farming_App.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final MailService mailService;
    public Account signup(SignUpRequest signUpRequest){
        repository.findByMail(signUpRequest.getEmail())
                        .ifPresent(a->{
                            throw new AccountAlreadyExistsException("Account", "email", signUpRequest.getEmail());
                        });
        Account account=new Account();
        account.setMail(signUpRequest.getEmail());
        account.setFirstname(signUpRequest.getFirstname());
        account.setLastname(signUpRequest.getLastname());
        account.setMail(signUpRequest.getEmail());
        account.setRole(roleRepository.findByName("USER"));
        account.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return repository.save(account);
    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getMail(),signInRequest.getPassword()));
        var user= repository.findByMail(signInRequest.getMail());


        if(user.isPresent()){
            var jwt= jwtService.generateToken(user.get());
            var refreshToken= jwtService.generateRefreshToken(new HashMap<>(),user.get());
            JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            return jwtAuthenticationResponse;
        }
        return null;
    }

    public int resetPassword(String mail,String newPassword){
        Account account= repository.findByMail(mail)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Account","mail",mail)
                );
        Account savedAccount = account;
        savedAccount.setPassword(newPassword);

        Account save = repository.save(savedAccount);

        if (save != null) {
            return 1;
        } else {
            return -1;
        }
    }
}
