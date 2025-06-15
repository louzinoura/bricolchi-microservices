package com.bricolchi.auth_service.service;

import com.bricolchi.auth_service.dto.LoginRequest;
import com.bricolchi.auth_service.dto.LoginResponse;
import com.bricolchi.auth_service.dto.RegisterRequest;
import com.bricolchi.auth_service.entity.Role;
import com.bricolchi.auth_service.entity.User;
import com.bricolchi.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        // Set PRESTATAIRE specific fields if role is PRESTATAIRE
        if (request.getRole() == Role.PRESTATAIRE) {
            user.setSpeciality(request.getSpeciality());
            user.setLocation(request.getLocation());
            user.setBio(request.getBio());
            user.setHourlyRate(request.getHourlyRate());
        }

        userRepository.save(user);
        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );

        return new LoginResponse(token, user.getUsername(), user.getRole(), user.getId());
    }
}