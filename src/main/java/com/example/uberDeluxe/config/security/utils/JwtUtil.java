package com.example.uberDeluxe.config.security.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtUtil {
    private final String jwtSecret;
}
