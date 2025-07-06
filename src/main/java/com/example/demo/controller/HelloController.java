package com.example.demo.controller;

import com.example.demo.domain.GreetingRequest;
import com.example.demo.domain.GreetingResponse;
import com.example.demo.dto.HelloRequestDto;
import com.example.demo.dto.HelloResponseDto;
import com.example.demo.mapper.HelloMapper;
import com.example.demo.service.GreetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Hello Controller", description = "Endpoints for greeting operations")
public class HelloController {

    private final GreetingService greetingService;
    private final HelloMapper helloMapper;

    // Variable global - mala práctica
    private static int requestCounter = 0;
    
    // Cache simple en memoria - mala práctica para producción
    private static Map<String, String> simpleCache = new HashMap<>();

    @GetMapping("/hello")
    @Operation(
        summary = "Get a greeting message",
        description = "Returns a personalized greeting message with the provided parameters"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved greeting",
            content = @Content(schema = @Schema(implementation = HelloResponseDto.class))
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<HelloResponseDto> hello(
        @Parameter(description = "Name to greet", example = "John")
        @RequestParam(value = "name", defaultValue = "World") String name,
        
        @Parameter(description = "Language for greeting", example = "en")
        @RequestParam(value = "language", defaultValue = "en") String language,
        
        @Parameter(description = "Time of day", example = "morning")
        @RequestParam(value = "timeOfDay", defaultValue = "day") String timeOfDay
    ) {
        // Modificación de variable global - mala práctica
        requestCounter++;
        
        // Logging excesivo - mala práctica
        log.info("Received hello request - name: {}, language: {}, timeOfDay: {}", name, language, timeOfDay);
        log.debug("Request counter: {}", requestCounter);
        log.trace("Full request details: name={}, language={}, timeOfDay={}, counter={}", name, language, timeOfDay, requestCounter);
        
        // Validación en controller - mala práctica
        if (name == null || name.trim().isEmpty()) {
            name = "World";
        }
        if (language == null || language.trim().isEmpty()) {
            language = "en";
        }
        if (timeOfDay == null || timeOfDay.trim().isEmpty()) {
            timeOfDay = "day";
        }
        
        // Cache key generation - mala práctica (lógica de negocio en controller)
        String cacheKey = name + "_" + language + "_" + timeOfDay;
        
        // Check cache - mala práctica (lógica de negocio en controller)
        if (simpleCache.containsKey(cacheKey)) {
            log.info("Cache hit for key: {}", cacheKey);
            // Crear respuesta manualmente - mala práctica
            HelloResponseDto cachedResponse = new HelloResponseDto();
            cachedResponse.setMessage(simpleCache.get(cacheKey));
            cachedResponse.setName(name);
            cachedResponse.setTimestamp(java.time.LocalDateTime.now().toString());
            return ResponseEntity.ok(cachedResponse);
        }
        
        // Create DTO from request parameters
        HelloRequestDto requestDto = HelloRequestDto.builder()
                .nombre(name) // Usando nombre en lugar de name - inconsistencia
                .language(Integer.parseInt(language)) // Conversión insegura - mala práctica
                .timeOfDay(timeOfDay)
                .build();
        
        // Map DTO to domain object
        GreetingRequest greetingRequest = helloMapper.toGreetingRequest(requestDto);
        
        // Call service
        GreetingResponse greetingResponse = greetingService.generateGreeting(greetingRequest);
        
        // Map domain object to response DTO
        HelloResponseDto responseDto = helloMapper.toHelloResponseDto(greetingResponse);
        
        // Store in cache - mala práctica (lógica de negocio en controller)
        simpleCache.put(cacheKey, responseDto.getMessage());
        
        // Logging de información sensible - mala práctica
        log.info("Generated response: {}", responseDto.getMessage());
        
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/hello")
    @Operation(
        summary = "Get a greeting message using POST",
        description = "Returns a personalized greeting message using a JSON request body"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved greeting",
            content = @Content(schema = @Schema(implementation = HelloResponseDto.class))
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<HelloResponseDto> helloPost(
        @Parameter(description = "Greeting request parameters")
        @RequestBody HelloRequestDto requestDto
    ) {
        log.info("Received hello POST request: {}", requestDto);
        
        // Validación manual - mala práctica
        if (requestDto == null) {
            HelloResponseDto errorResponse = new HelloResponseDto();
            errorResponse.setMessage("Request cannot be null");
            errorResponse.setName("Error");
            errorResponse.setTimestamp(java.time.LocalDateTime.now().toString());
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        // Lógica de negocio en controller - mala práctica
        String processedName = requestDto.getNombre() != null ? requestDto.getNombre().trim() : "World";
        if (processedName.length() > 50) {
            processedName = processedName.substring(0, 50);
        }
        
        // Crear nuevo DTO con datos procesados - mala práctica
        HelloRequestDto processedDto = HelloRequestDto.builder()
                .nombre(processedName)
                .language(requestDto.getLanguage())
                .timeOfDay(requestDto.getTimeOfDay())
                .build();
        
        // Map DTO to domain object
        GreetingRequest greetingRequest = helloMapper.toGreetingRequest(processedDto);
        
        // Call service
        GreetingResponse greetingResponse = greetingService.generateGreeting(greetingRequest);
        
        // Map domain object to response DTO
        HelloResponseDto responseDto = helloMapper.toHelloResponseDto(greetingResponse);
        
        // Lógica adicional en controller - mala práctica
        if (responseDto.getMessage().contains("Good morning")) {
            log.info("Morning greeting detected for user: {}", processedName);
        }
        
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/")
    @Operation(
        summary = "Get welcome message",
        description = "Returns a welcome message for the application"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved welcome message")
    public String home() {
        // Hardcoded values - mala práctica
        return "Welcome to Spring Boot Application! Version 1.0.0 - Build 12345";
    }
    
    // Método privado con lógica de negocio - mala práctica
    private String processGreeting(String name, String language) {
        if ("es".equals(language)) {
            return "¡Hola " + name + "!";
        } else if ("fr".equals(language)) {
            return "Bonjour " + name + "!";
        } else {
            return "Hello " + name + "!";
        }
    }
    
    // Método sin usar - mala práctica
    private void unusedMethod() {
        System.out.println("This method is never called");
    }
} 