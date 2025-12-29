package com.example.chatbot.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CrisisDetectionService {
    
    private static final List<String> CRISIS_KEYWORDS = Arrays.asList(
        "suicide", "kill myself", "end it all", "don't want to live",
        "hopeless", "can't go on", "nothing matters", "want to die",
        "end my life", "no reason to live", "give up", "can't take it"
    );
    
    private static final List<String> URGENT_KEYWORDS = Arrays.asList(
        "emergency", "help me", "immediate help", "urgent",
        "serious", "critical", "dangerous", "hurting myself"
    );
    
    public boolean detectCrisis(String message) {
        String lowerMessage = message.toLowerCase();
        
        // Check for crisis keywords
        boolean hasCrisisKeyword = CRISIS_KEYWORDS.stream()
            .anyMatch(lowerMessage::contains);
        
        // Check for urgent keywords
        boolean hasUrgentKeyword = URGENT_KEYWORDS.stream()
            .anyMatch(lowerMessage::contains);
        
        // Check for intensity indicators
        boolean hasIntensity = lowerMessage.contains("really") && 
                              (lowerMessage.contains("bad") || 
                               lowerMessage.contains("terrible") ||
                               lowerMessage.contains("awful"));
        
        return hasCrisisKeyword || (hasUrgentKeyword && hasIntensity);
    }
    
    public String getCrisisLevel(String message) {
        String lowerMessage = message.toLowerCase();
        
        if (CRISIS_KEYWORDS.stream().anyMatch(lowerMessage::contains)) {
            return "CRITICAL";
        }
        
        if (URGENT_KEYWORDS.stream().anyMatch(lowerMessage::contains)) {
            return "HIGH";
        }
        
        return "NORMAL";
    }
    
    public List<String> getEmergencyContacts() {
        return Arrays.asList(
            "KIRAN Helpline: 1800-599-0019",
            "Emergency Services: 112/911",
            "Suicide Prevention: 9152987821",
            "iCall Psychosocial Helpline: 9152987821"
        );
    }
}