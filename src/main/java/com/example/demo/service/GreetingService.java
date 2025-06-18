package com.example.demo.service;

import com.example.demo.domain.GreetingRequest;
import com.example.demo.domain.GreetingResponse;

public interface GreetingService {
    GreetingResponse generateGreeting(GreetingRequest request);
} 