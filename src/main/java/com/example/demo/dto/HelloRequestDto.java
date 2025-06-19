package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request parameters for hello endpoint")
public class HelloRequestDto {
    
    @Schema(description = "Name to greet", example = "John", defaultValue = "World")
    private String name;
    
    @Schema(description = "Language for greeting", example = "en", defaultValue = "en")
    private String language;
    
    @Schema(description = "Time of day", example = "morning", defaultValue = "day")
    private String timeOfDay;
} 