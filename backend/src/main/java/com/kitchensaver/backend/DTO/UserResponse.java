package com.kitchensaver.backend.DTO;

import com.kitchensaver.backend.model.Users;

public class UserResponse {
    private String token;
    private String message;
    private String role;
    private Users user;

    public UserResponse(String message, String token, Users user) {
        this.token = token;
        this.user = user;
        this.role = user.getRole().name();
        this.message = message;
    }

    public UserResponse(String message, String token) {
        this.token = token;
        this.role = null;

        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUser() {
        return user;
    }

    public String getRole() {
        return role;
    }

    public void setUser(Users user) {
        this.role = user.getRole().name();
        this.user = user;
    }
}