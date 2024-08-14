package com.example.Farming_App.services.impl;

import com.example.Farming_App.entity.Account;
import com.example.Farming_App.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {


    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // ngay phat hanh
                .setExpiration(new Date(System.currentTimeMillis() +1000*60*24)) // ngay het han
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public String generateRefreshToken(HashMap<String, Object> extractClaim, UserDetails userDetails) {
        return Jwts.builder().setClaims(extractClaim).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // ngay phat hanh
                .setExpiration(new Date(System.currentTimeMillis() +1000*1296000)) // ngay het han
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimResolvers){
        final Claims claims=extractAllClaim(token);
        return claimResolvers.apply(claims);
    }


    // claims la phan payload hay la phan chua thong tin doi tuong,ngay batdau,het han...
    private Claims extractAllClaim(String token) {
        return Jwts.parser().setSigningKey(getSigninKey()).build()
                .parseClaimsJws(token).getBody();
    }

    private Key getSigninKey() {
        byte[] key= Decoders.BASE64.decode("357638792F423F4428472B4B6250655368566D597133743677397A2443264629");
        return Keys.hmacShaKeyFor(key);
    }


    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }
}
