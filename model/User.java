package com.example.chatbot.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private String id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private Boolean active;
    
    public User() {
        this.id = java.util.UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
}