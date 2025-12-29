package com.example.chatbot.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatService {
    
    public Map<String, Object> processMessage(String message, String userId) {
        Map<String, Object> response = new HashMap<>();
        
        // Simple sentiment analysis
        Map<String, Object> sentiment = analyzeSentiment(message);
        
        // Generate response
        String reply = generateResponse(message, sentiment);
        
        // Check for crisis
        boolean isCrisis = checkCrisis(message);
        
        response.put("reply", reply);
        response.put("sentiment", sentiment);
        response.put("userId", userId);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("crisisAlert", isCrisis);
        
        if (isCrisis) {
            response.put("emergencyContacts", getEmergencyContacts());
        }
        
        return response;
    }
    
    private Map<String, Object> analyzeSentiment(String message) {
        String lower = message.toLowerCase();
        Map<String, Object> sentiment = new HashMap<>();
        
        if (lower.contains("sad") || lower.contains("depressed") || lower.contains("unhappy")) {
            sentiment.put("emotion", "sad");
            sentiment.put("score", -0.8);
            sentiment.put("level", "high_negative");
        } else if (lower.contains("anxious") || lower.contains("stress") || lower.contains("worried")) {
            sentiment.put("emotion", "anxious");
            sentiment.put("score", -0.6);
            sentiment.put("level", "medium_negative");
        } else if (lower.contains("happy") || lower.contains("good") || lower.contains("great")) {
            sentiment.put("emotion", "happy");
            sentiment.put("score", 0.8);
            sentiment.put("level", "high_positive");
        } else if (lower.contains("angry") || lower.contains("mad") || lower.contains("frustrated")) {
            sentiment.put("emotion", "angry");
            sentiment.put("score", -0.7);
            sentiment.put("level", "high_negative");
        } else {
            sentiment.put("emotion", "neutral");
            sentiment.put("score", 0.0);
            sentiment.put("level", "neutral");
        }
        
        return sentiment;
    }
    
    private String generateResponse(String message, Map<String, Object> sentiment) {
        String emotion = (String) sentiment.get("emotion");
        
        Map<String, String> responses = new HashMap<>();
        responses.put("sad", "I'm really sorry you're feeling sad. It's okay to feel this way. Would you like to talk about it?");
        responses.put("anxious", "I understand anxiety can be overwhelming. Let's try some breathing exercises together.");
        responses.put("angry", "Anger is a natural emotion. Sometimes writing down what's bothering you can help.");
        responses.put("happy", "That's wonderful to hear! I'm glad you're feeling happy today!");
        responses.put("neutral", "Thanks for sharing. I'm here to listen whenever you need to talk.");
        
        return responses.getOrDefault(emotion, "I'm here for you. Tell me more about how you're feeling.");
    }
    
    private boolean checkCrisis(String message) {
        String lower = message.toLowerCase();
        String[] crisisKeywords = {"suicide", "kill myself", "end my life", "want to die", "can't go on"};
        
        for (String keyword : crisisKeywords) {
            if (lower.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
    
    private List<String> getEmergencyContacts() {
        return Arrays.asList(
            "KIRAN Helpline: 1800-599-0019",
            "Emergency Services: 112/911",
            "Suicide Prevention: 9152987821",
            "iCall: 9152987821"
        );
    }
}