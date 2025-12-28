package com.example.chatbot.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatMessage {
    private String id;
    private String userId;
    private String message;
    private String response;
    private String sentiment;
    private String emotion;
    private Double sentimentScore;
    private LocalDateTime timestamp;
    private Boolean crisisAlert;
    
    public ChatMessage() {
        this.id = java.util.UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.crisisAlert = false;
    }
}