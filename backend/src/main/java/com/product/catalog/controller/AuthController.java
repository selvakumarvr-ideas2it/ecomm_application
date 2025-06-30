package com.product.catalog.controller;

import com.product.catalog.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        if (userService.validateUser(request.getUsername(), request.getPassword()).isPresent()) {
            Map<String, Object> success = new HashMap<>();
            success.put("token", "dummy-jwt-token");
            return ResponseEntity.ok(success);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid username or password");
            return ResponseEntity.status(401).body(error);
        }
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
} 