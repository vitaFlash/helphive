package com.hive.help.controller;

import com.hive.help.dto.user.*;
import com.hive.help.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
public class UserController {

    private final UserService service;

    @PostMapping
    public UserDto create(@RequestBody UserCreateDto dto) { return service.create(dto); }

    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @GetMapping
    public Page<UserDto> list(@RequestParam(required = false) String role,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        return service.listByRole(role, page, size);
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) { return service.get(id); }

    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @PatchMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserUpdateDto dto) {
        return service.update(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}