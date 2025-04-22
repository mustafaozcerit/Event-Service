package com.opthema.hcm.java_microservice.application.service;

import com.opthema.hcm.java_microservice.domain.model.AppUser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-time}")
    private Long EXPIRATION_TIME;

    private SecretKey KEY;
    // Initializes the key after the class is instantiated and the jwtSecret is injected,
    // preventing the repeated creation of the key and enhancing performance
    @PostConstruct
    public void init() {
        this.KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Token'ları cache'de tutmak için ConcurrentHashMap kullanıyoruz
    private final ConcurrentHashMap<Long, String> tokenCache = new ConcurrentHashMap<>();

    @Override
    public String generateToken(AppUser user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY , SignatureAlgorithm.HS256)  // Byte dizisi kullanıldı
                .compact();
    }

    // Kullanıcının mevcut token'ını almak için metod
    @Override
    public String getExistingToken(Long userId) {
        String existingToken = tokenCache.get(userId);

        // Token hala geçerli mi kontrolü
        if(validateToken(existingToken)){
            return  existingToken;
        }
        return null;
    }

    // Yeni token'ı cache'e kaydetmek için metod
    @Override
    public void saveToken(Long userId, String token) {
        // Token'ı cache'e kaydediyoruz
        tokenCache.put(userId, token);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true; // Token geçerli
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw e; // Token geçersiz
        }
    }

    @Override
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
