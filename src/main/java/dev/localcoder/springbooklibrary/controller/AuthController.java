package dev.localcoder.springbooklibrary.controller;

import dev.localcoder.springbooklibrary.dto.auth.AuthResponse;
import dev.localcoder.springbooklibrary.dto.auth.LoginRequest;
import dev.localcoder.springbooklibrary.dto.auth.RegisterRequest;
import dev.localcoder.springbooklibrary.dto.reader.ReaderResponse;
import dev.localcoder.springbooklibrary.repository.ReaderRepository;
import dev.localcoder.springbooklibrary.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final ReaderRepository readerRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<ReaderResponse> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        ReaderResponse reader = authService.getCurrentUser(email);
        return ResponseEntity.ok(reader);
    }
}
