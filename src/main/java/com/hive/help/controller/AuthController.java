package com.hive.help.controller;

import com.hive.help.dto.auth.AuthResponse;
import com.hive.help.dto.auth.LoginRequest;
import com.hive.help.model.User;
import com.hive.help.repository.UserRepository;
import com.hive.help.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        User u = userRepository.findByEmail(req.email()).orElseThrow();

        String token = jwtService.generateToken(
                u.getEmail(),
                Map.of(
                        "role", u.getRole().name(),
                        "name", u.getName(),
                        "id", u.getId()
                )
        );

        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }
}

