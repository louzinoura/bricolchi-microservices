package com.bricolchi.auth_service.controller;

import com.bricolchi.auth_service.dto.UserProfileResponse;
import com.bricolchi.auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CLIENT') or hasRole('PRESTATAIRE')")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        UserProfileResponse profile = userService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('PRESTATAIRE')")
    public ResponseEntity<String> updateProfile(@RequestBody UserProfileResponse updateRequest,
                                                Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        String response = userService.updateProfile(userId, updateRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PRESTATAIRE')")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        UserProfileResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
