package com.hive.help.dto.user;

import com.hive.help.model.Role;

public record UserUpdateDto(
        String name,
        Role role,
        Boolean active
) {}
