package com.hive.help.dto.user;

import com.hive.help.model.Role;
import java.time.LocalDateTime;

public record UserDto(
        Long id,
        String name,
        String email,
        Role role,
        Boolean active,
        LocalDateTime registrationDate
) {}

