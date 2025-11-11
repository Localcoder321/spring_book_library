package dev.localcoder.springbooklibrary.service;

import dev.localcoder.springbooklibrary.dto.auth.AuthResponse;
import dev.localcoder.springbooklibrary.dto.auth.LoginRequest;
import dev.localcoder.springbooklibrary.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
