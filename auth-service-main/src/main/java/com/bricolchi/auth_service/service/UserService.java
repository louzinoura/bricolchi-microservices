package com.bricolchi.auth_service.service;

import com.bricolchi.auth_service.dto.UserProfileResponse;
import com.bricolchi.auth_service.entity.User;
import com.bricolchi.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserProfileResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getSpeciality(),
                user.getLocation(),
                user.getBio(),
                user.getHourlyRate(),
                user.getCreatedAt()
        );
    }

    public UserProfileResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getSpeciality(),
                user.getLocation(),
                user.getBio(),
                user.getHourlyRate(),
                user.getCreatedAt()
        );
    }

    public String updateProfile(Long userId, UserProfileResponse updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields (simplified - in real app, you'd have a proper update DTO)
        user.setBio(updateRequest.getBio());
        user.setLocation(updateRequest.getLocation());
        user.setHourlyRate(updateRequest.getHourlyRate());

        userRepository.save(user);
        return "Profile updated successfully";
    }
}
