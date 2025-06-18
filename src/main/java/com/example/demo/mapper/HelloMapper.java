package com.example.demo.mapper;

import com.example.demo.domain.GreetingRequest;
import com.example.demo.domain.GreetingResponse;
import com.example.demo.dto.HelloRequestDto;
import com.example.demo.dto.HelloResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HelloMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "timeOfDay", source = "timeOfDay")
    GreetingRequest toGreetingRequest(HelloRequestDto dto);

    @Mapping(target = "message", source = "message")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "timestamp", source = "timestamp")
    HelloResponseDto toHelloResponseDto(GreetingResponse response);

    @Named("defaultName")
    default String defaultName(String name) {
        return name != null && !name.trim().isEmpty() ? name : "World";
    }

    @Named("defaultLanguage")
    default String defaultLanguage(String language) {
        return language != null && !language.trim().isEmpty() ? language : "en";
    }

    @Named("defaultTimeOfDay")
    default String defaultTimeOfDay(String timeOfDay) {
        return timeOfDay != null && !timeOfDay.trim().isEmpty() ? timeOfDay : "day";
    }
} 