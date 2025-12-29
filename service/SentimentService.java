package com.example.chatbot.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SentimentService {
    
    private static final Map<String, Double> EMOTION_KEYWORDS = Map.ofEntries(
        Map.entry("sad", -0.8),
        Map.entry("depressed", -0.9),
        Map.entry("anxious", -0.7),
        Map.entry("stress", -0.6),
        Map.entry("panic", -0.9),
        Map.entry("happy", 0.8),
        Map.entry("good", 0.6),
        Map.entry("excited", 0.7),
        Map.entry("calm", 0.5),
        Map.entry("angry", -0.7),
        Map.entry("worried", -0.6),
        Map.entry("scared", -0.7),
        Map.entry("lonely", -0.8),
        Map.entry("hopeless", -0.9),
        Map.entry("overwhelmed", -0.8)
    );
    
    public Map<String, Object> analyze(String message) {
        String lowerMessage = message.toLowerCase();
        
        double score = 0;
        int keywordCount = 0;
        List<String> detectedEmotions = new ArrayList<>();
        
        for (Map.Entry<String, Double> entry : EMOTION_KEYWORDS.entrySet()) {
            if (lowerMessage.contains(entry.getKey())) {
                score += entry.getValue();
                keywordCount++;
                detectedEmotions.add(entry.getKey());
            }
        }
        
        // Context-based adjustments
        if (lowerMessage.contains("not good") || lowerMessage.contains("not okay")) {
            score -= 0.5;
        }
        
        if (lowerMessage.contains("really good") || lowerMessage.contains("very happy")) {
            score += 0.5;
        }
        
        // Calculate average score
        if (keywordCount > 0) {
            score = score / keywordCount;
        }
        
        // Clamp between -1 and 1
        score = Math.max(-1.0, Math.min(1.0, score));
        
        String emotion = determineEmotion(score, detectedEmotions);
        String sentiment = determineSentiment(score);
        
        Map<String, Object> result = new HashMap<>();
        result.put("score", score);
        result.put("emotion", emotion);
        result.put("sentiment", sentiment);
        result.put("keywords", detectedEmotions);
        result.put("intensity", Math.abs(score));
        
        return result;
    }
    
    private String determineEmotion(double score, List<String> emotions) {
        if (emotions.isEmpty()) {
            return score >= 0 ? "neutral" : "concerned";
        }
        
        if (score <= -0.7) return "distressed";
        if (score <= -0.3) return "sad";
        if (score <= 0) return "concerned";
        if (score <= 0.3) return "neutral";
        if (score <= 0.7) return "positive";
        return "happy";
    }
    
    private String determineSentiment(double score) {
        if (score <= -0.7) return "very_negative";
        if (score <= -0.3) return "negative";
        if (score <= 0) return "slightly_negative";
        if (score <= 0.3) return "slightly_positive";
        if (score <= 0.7) return "positive";
        return "very_positive";
    }
}