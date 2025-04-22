package com.opthema.hcm.java_microservice.web.controller;

import com.opthema.hcm.java_microservice.application.service.AuthService;
import com.opthema.hcm.java_microservice.domain.dto.RequestUserDto;
import com.opthema.hcm.java_microservice.domain.dto.AppUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RequestUserDto loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String token = authService.authenticate(username, password);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AppUserDTO appUser){
        return authService.register(appUser);
    }
}
