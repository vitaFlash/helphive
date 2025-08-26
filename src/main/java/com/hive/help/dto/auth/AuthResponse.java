package com.hive.help.dto.auth;

public record AuthResponse(
        String accessToken,
        String tokenType
) {}