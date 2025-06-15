package com.bricolchi.auth_service.dto;


import com.bricolchi.auth_service.entity.Role;

public class LoginResponse {
    private String token;
    private String username;
    private Role role;
    private Long userId;

    public LoginResponse(String token, String username, Role role, Long userId) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.userId = userId;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
