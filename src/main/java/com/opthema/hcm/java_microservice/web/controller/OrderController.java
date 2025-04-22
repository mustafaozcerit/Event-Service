package com.opthema.hcm.java_microservice.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')") // Sadece 'ROLE_USER' olanlar erişebilir
    public ResponseEntity<String> orders() {
        return ResponseEntity.ok("Hello word");
    }
}
