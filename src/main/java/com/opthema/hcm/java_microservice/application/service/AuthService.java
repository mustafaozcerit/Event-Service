package com.opthema.hcm.java_microservice.application.service;

import com.opthema.hcm.java_microservice.domain.dto.AppUserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String authenticate(String username, String password);
    ResponseEntity<String> register(AppUserDTO userDTO);
}
