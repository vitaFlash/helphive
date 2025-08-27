package com.hive.help.service;

import com.hive.help.dto.user.*;
import org.springframework.data.domain.*;

public interface UserService {
    UserDto create(UserCreateDto dto);
    Page<UserDto> listByRole(String role, int page, int size);
    UserDto get(Long id);
    UserDto update(Long id, UserUpdateDto dto);
    void delete(Long id);
}
