package com.secureapp.Charichat.controller;

import com.secureapp.Charichat.dto.LoginRequest;
import com.secureapp.Charichat.dto.RegisterRequest;
import com.secureapp.Charichat.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        authService.register(
                request.get("email"),
                request.get("password"),
                request.get("displayName"),
                request.get("publicKey")
        );
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String token = authService.login(
                request.get("email"),
                request.get("password")
        );
        return ResponseEntity.ok(Map.of("token", token));
    }
}
