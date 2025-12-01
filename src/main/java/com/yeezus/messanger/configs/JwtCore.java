package com.yeezus.messanger.configs;


import com.yeezus.messanger.entities.UserDetailsImpl;
import com.yeezus.messanger.entities.users;
import com.yeezus.messanger.repositories.userRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtCore {
    @Value("${spring.token.signing.secret}")
    private String secret;
    @Value("${spring.token.signing.expirationMs}")
    private int lifetime;

    private final userRepository userRepository;

    public JwtCore(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("email", userDetails.getEmail());
        claims.put("id", userDetails.getId());
        return Jwts.builder()
                .setSubject(userDetails.getEmail()).setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(new Date(new Date().getTime() + lifetime))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getBody().get("email", String.class);
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getBody().get("username", String.class);
    }

    public Long getIdFromToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getBody().get("id", Long.class);
    }

    public users getUserFromToken(String token){
        Long UserId = getIdFromToken(token);
        return userRepository.findById(UserId).orElseThrow();
    }


}
