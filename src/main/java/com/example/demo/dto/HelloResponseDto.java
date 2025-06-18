package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response from hello endpoint")
public class HelloResponseDto {
    
    @Schema(description = "Greeting message", example = "Hello John!")
    private String message;
    
    @Schema(description = "Name that was greeted", example = "John")
    private String name;
    
    @Schema(description = "Timestamp of the greeting", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;
} 