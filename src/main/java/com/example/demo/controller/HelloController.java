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

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Hello Controller", description = "Endpoints for greeting operations")
public class HelloController {

    private final GreetingService greetingService;
    private final HelloMapper helloMapper;

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
        log.info("Received hello request - name: {}, language: {}, timeOfDay: {}", name, language, timeOfDay);
        
        // Create DTO from request parameters
        HelloRequestDto requestDto = HelloRequestDto.builder()
                .name(name)
                .language(language)
                .timeOfDay(timeOfDay)
                .build();
        
        // Map DTO to domain object
        GreetingRequest greetingRequest = helloMapper.toGreetingRequest(requestDto);
        
        // Call service
        GreetingResponse greetingResponse = greetingService.generateGreeting(greetingRequest);
        
        // Map domain object to response DTO
        HelloResponseDto responseDto = helloMapper.toHelloResponseDto(greetingResponse);
        
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
        
        // Map DTO to domain object
        GreetingRequest greetingRequest = helloMapper.toGreetingRequest(requestDto);
        
        // Call service
        GreetingResponse greetingResponse = greetingService.generateGreeting(greetingRequest);
        
        // Map domain object to response DTO
        HelloResponseDto responseDto = helloMapper.toHelloResponseDto(greetingResponse);
        
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/")
    @Operation(
        summary = "Get welcome message",
        description = "Returns a welcome message for the application"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved welcome message")
    public String home() {
        return "Welcome to Spring Boot Application!";
    }
} 