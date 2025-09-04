package com.hive.help.dto.auth;

import java.util.Set;

public record AuthResponse(
        String token,
        Long userId,
        String email,
        Set<String> roles
) {}
