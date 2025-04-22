package com.opthema.hcm.java_microservice.application.service;

import com.opthema.hcm.java_microservice.domain.model.AppUser;

public interface JwtService {
    String generateToken(AppUser user); // JWT üretir
    boolean validateToken(String token); // Token geçerli mi?
    String extractUsername(String token); // Token'dan kullanıcı adını çıkarır
    String getExistingToken(Long userId);  // Mevcut token'ı alır
    void saveToken(Long userId, String token);  // Yeni token'ı kaydeder
}
