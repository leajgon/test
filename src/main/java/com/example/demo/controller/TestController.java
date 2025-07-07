package com.example.demo.controller;

import com.example.demo.domain.GreetingRequest;
import com.example.demo.domain.GreetingResponse;
import com.example.demo.dto.HelloRequestDto;
import com.example.demo.dto.HelloResponseDto;
import com.example.demo.mapper.HelloMapper;
import com.example.demo.service.GreetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Tag(name = "Test Controller", description = "Test endpoints with bad practices")
public class TestController {

    private final GreetingService greetingService;
    private final HelloMapper helloMapper;

    // Variable global - mala práctica
    private static String globalVariable = "test";
    
    // Lista mutable compartida - mala práctica
    private static List<String> sharedList = new ArrayList<>();

    @GetMapping("/bad-practices")
    @Operation(summary = "Endpoint with intentional bad practices")
    public Map<String, Object> badPracticesEndpoint(
            @RequestParam(required = false) String param1,
            @RequestParam(required = false) String param2,
            @RequestParam(required = false) String param3,
            @RequestParam(required = false) String param4,
            @RequestParam(required = false) String param5,
            @RequestParam(required = false) String param6,
            @RequestParam(required = false) String param7,
            @RequestParam(required = false) String param8,
            @RequestParam(required = false) String param9,
            @RequestParam(required = false) String param10
    ) {
        // Múltiples responsabilidades en un solo método - mala práctica
        Map<String, Object> result = new HashMap<>();
        
        // Lógica de negocio en el controller - mala práctica
        String processedParam1 = param1 != null ? param1.toUpperCase() : "DEFAULT";
        String processedParam2 = param2 != null ? param2.toLowerCase() : "default";
        
        // Modificación de variable global - mala práctica
        globalVariable = processedParam1;
        sharedList.add(processedParam2);
        
        // Hardcoded values - mala práctica
        result.put("status", "SUCCESS");
        result.put("code", 200);
        result.put("message", "Operation completed");
        result.put("data", processedParam1 + " " + processedParam2);
        result.put("globalVar", globalVariable);
        result.put("sharedListSize", sharedList.size());
        
        // Lógica compleja inline - mala práctica
        if (param3 != null && param3.length() > 5) {
            result.put("extra", "Long parameter detected");
            if (param4 != null && param4.contains("test")) {
                result.put("testFlag", true);
                if (param5 != null && param5.equals("admin")) {
                    result.put("adminAccess", true);
                    result.put("sensitiveData", "This should not be exposed");
                }
            }
        }
        
        // Múltiples return points - mala práctica
        if (param6 != null) {
            Map<String, Object> earlyReturn = new HashMap<>();
            earlyReturn.put("early", "return");
            earlyReturn.put("reason", param6);
            return earlyReturn;
        }
        
        // Logging sensitive information - mala práctica
        System.out.println("Processing request with params: " + param1 + ", " + param2 + ", " + param3);
        
        return result;
    }

    @PostMapping("/complex-logic")
    @Operation(summary = "Complex logic endpoint with bad practices")
    public HelloResponseDto complexLogicEndpoint(@RequestBody HelloRequestDto request) {
        // Múltiples responsabilidades - mala práctica
        HelloResponseDto response = new HelloResponseDto();
        
        // Lógica de validación en controller - mala práctica
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            response.setMessage("Error: Name is required");
            response.setName("Unknown");
            response.setTimestamp(LocalDateTime.now().toString());
            return response;
        }
        
        // Lógica de negocio compleja en controller - mala práctica
        String processedName = request.getNombre();
        if (processedName.length() > 10) {
            processedName = processedName.substring(0, 10) + "...";
        }
        
        // Creación directa de objetos de dominio - mala práctica
        GreetingRequest domainRequest = new GreetingRequest();
        domainRequest.setName(processedName);
        domainRequest.setLanguage(request.getLanguage() != null ? request.getLanguage().toString() : "en");
        domainRequest.setTimeOfDay(request.getTimeOfDay());
        
        // Llamada al servicio
        GreetingResponse domainResponse = greetingService.generateGreeting(domainRequest);
        
        // Mapeo manual en lugar de usar mapper - mala práctica
        response.setMessage(domainResponse.getMessage());
        response.setName(domainResponse.getName());
        response.setTimestamp(domainResponse.getTimestamp().toString());
        
        // Lógica adicional en controller - mala práctica
        if (response.getMessage().contains("Good morning")) {
            // response.setSuccess(true); // Campo no existe, pero es mala práctica
        }
        
        return response;
    }

    @GetMapping("/security-issues")
    @Operation(summary = "Endpoint with security issues")
    public String securityIssuesEndpoint(@RequestParam String userInput) {
        // SQL Injection vulnerable - mala práctica
        String query = "SELECT * FROM users WHERE name = '" + userInput + "'";
        
        // XSS vulnerable - mala práctica
        String htmlResponse = "<h1>Welcome " + userInput + "</h1>";
        
        // Hardcoded credentials - mala práctica
        String password = "admin123";
        String apiKey = "sk-1234567890abcdef";
        
        // Logging sensitive data - mala práctica
        System.out.println("User input: " + userInput);
        System.out.println("Password: " + password);
        
        return htmlResponse + " Query: " + query;
    }

    @GetMapping("/performance-issues")
    @Operation(summary = "Endpoint with performance issues")
    public List<String> performanceIssuesEndpoint() {
        List<String> result = new ArrayList<>();
        
        // N+1 problem simulation - mala práctica
        for (int i = 0; i < 1000; i++) {
            // Simulating database call in loop
            String item = "Item " + i + " processed at " + System.currentTimeMillis();
            result.add(item);
        }
        
        // Memory leak simulation - mala práctica
        for (int i = 0; i < 10000; i++) {
            sharedList.add("Memory leak item " + i);
        }
        
        return result;
    }
} 