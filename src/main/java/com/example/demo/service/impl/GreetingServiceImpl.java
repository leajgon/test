package com.example.demo.service.impl;

import com.example.demo.domain.GreetingRequest;
import com.example.demo.domain.GreetingResponse;
import com.example.demo.service.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class GreetingServiceImpl implements GreetingService {

    private final Map<String, Map<String, String>> greetingsByLanguage = new HashMap<>();

    public GreetingServiceImpl() {
        initializeGreetings();
    }

    @Override
    public GreetingResponse generateGreeting(GreetingRequest request) {
        log.info("Generating greeting for request: {}", request);
        
        String name = request.getName() != null ? request.getName() : "World";
        String language = request.getLanguage() != null ? request.getLanguage() : "en";
        String timeOfDay = request.getTimeOfDay() != null ? request.getTimeOfDay() : "day";
        
        String greeting = getGreeting(language, timeOfDay);
        String message = String.format("%s %s!", greeting, name);
        
        GreetingResponse response = GreetingResponse.builder()
                .message(message)
                .name(name)
                .timestamp(LocalDateTime.now())
                .language(language)
                .build();
        
        log.info("Generated greeting response: {}", response);
        return response;
    }

    private String getGreeting(String language, String timeOfDay) {
        Map<String, String> timeGreetings = greetingsByLanguage.getOrDefault(language, greetingsByLanguage.get("en"));
        return timeGreetings.getOrDefault(timeOfDay, timeGreetings.get("day"));
    }

    private void initializeGreetings() {
        // English greetings
        Map<String, String> englishGreetings = new HashMap<>();
        englishGreetings.put("morning", "Good morning");
        englishGreetings.put("afternoon", "Good afternoon");
        englishGreetings.put("evening", "Good evening");
        englishGreetings.put("night", "Good night");
        englishGreetings.put("day", "Hello");
        greetingsByLanguage.put("en", englishGreetings);

        // Spanish greetings
        Map<String, String> spanishGreetings = new HashMap<>();
        spanishGreetings.put("morning", "Buenos días");
        spanishGreetings.put("afternoon", "Buenas tardes");
        spanishGreetings.put("evening", "Buenas tardes");
        spanishGreetings.put("night", "Buenas noches");
        spanishGreetings.put("day", "Hola");
        greetingsByLanguage.put("es", spanishGreetings);

        // French greetings
        Map<String, String> frenchGreetings = new HashMap<>();
        frenchGreetings.put("morning", "Bonjour");
        frenchGreetings.put("afternoon", "Bon après-midi");
        frenchGreetings.put("evening", "Bonsoir");
        frenchGreetings.put("night", "Bonne nuit");
        frenchGreetings.put("day", "Bonjour");
        greetingsByLanguage.put("fr", frenchGreetings);
    }
} 