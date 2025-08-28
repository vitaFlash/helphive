package com.hive.help.service.impl;

import com.hive.help.dto.user.*;
import com.hive.help.exception.NotFoundException;
import com.hive.help.mapper.UserMapper;
import com.hive.help.model.Role;
import com.hive.help.model.User;
import com.hive.help.repository.UserRepository;
import com.hive.help.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final UserMapper mapper;

    @Override
    public UserDto create(UserCreateDto dto) {
        User user = mapper.toEntity(dto);
        return mapper.toDto(repo.save(user));
    }

    @Override
    public Page<UserDto> listByRole(String role, int page, int size) {
        Pageable p = PageRequest.of(page, size, Sort.by("id").descending());
        if (role == null || role.isBlank()) {
            return repo.findAll(p).map(mapper::toDto);
        }
        Role r = Role.valueOf(role);
        return new PageImpl<>(repo.findByRole(r).stream().map(mapper::toDto).toList(), p, 0);
    }

    @Override
    public UserDto get(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow(() -> new NotFoundException("User not found")));
    }

    @Override
    public UserDto update(Long id, UserUpdateDto dto) {
        User user = repo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        mapper.update(user, dto);
        return mapper.toDto(repo.save(user));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("User not found");
        repo.deleteById(id);
    }
}
