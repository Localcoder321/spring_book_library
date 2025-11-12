package dev.localcoder.springbooklibrary.service;

import dev.localcoder.springbooklibrary.dto.auth.AuthResponse;
import dev.localcoder.springbooklibrary.dto.auth.LoginRequest;
import dev.localcoder.springbooklibrary.dto.auth.RegisterRequest;
import dev.localcoder.springbooklibrary.dto.reader.ReaderResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    ReaderResponse getCurrentUser(String email);
}
