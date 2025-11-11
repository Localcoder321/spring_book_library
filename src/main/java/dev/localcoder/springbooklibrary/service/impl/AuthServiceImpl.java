package dev.localcoder.springbooklibrary.service.impl;

import dev.localcoder.springbooklibrary.dto.auth.AuthResponse;
import dev.localcoder.springbooklibrary.dto.auth.LoginRequest;
import dev.localcoder.springbooklibrary.dto.auth.RegisterRequest;
import dev.localcoder.springbooklibrary.entity.ReaderEntity;
import dev.localcoder.springbooklibrary.entity.RoleEntity;
import dev.localcoder.springbooklibrary.repository.ReaderRepository;
import dev.localcoder.springbooklibrary.repository.RoleRepository;
import dev.localcoder.springbooklibrary.security.JwtService;
import dev.localcoder.springbooklibrary.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ReaderRepository readerRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if(readerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already registered");
        }

        RoleEntity userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("ROLE_USER not found in DB"));
        ReaderEntity reader = new ReaderEntity();
        reader.setName(request.getName());
        reader.setEmail(request.getEmail());
        reader.setPassword(passwordEncoder.encode(request.getPassword()));
        reader.getRoles().add(userRole);
        readerRepository.save(reader);

        Set<String> roleNames = reader.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet());
        String token = jwtService.generateToken(reader.getEmail(), roleNames);
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        ReaderEntity reader = readerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if(!passwordEncoder.matches(request.getPassword(), reader.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        Set<String> roleNames = reader.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet());
        String token = jwtService.generateToken(reader.getEmail(), roleNames);
        return new AuthResponse(token);
    }
}
