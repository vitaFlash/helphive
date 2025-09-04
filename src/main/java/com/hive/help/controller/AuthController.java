package com.hive.help.controller;

import com.hive.help.dto.auth.AuthResponse;
import com.hive.help.dto.auth.LoginRequest;
import com.hive.help.model.User;
import com.hive.help.repository.UserRepository;
import com.hive.help.security.CustomUserPrincipal;
import com.hive.help.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails principal = (UserDetails) auth.getPrincipal();
        Long uid;
        String email = principal.getUsername();

        if (principal instanceof CustomUserPrincipal cup) {
            uid = cup.getId();
            email = cup.getUsername();
        } else {
            uid = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"))
                    .getId();
        }

        String token = jwtTokenProvider.generateToken(principal, uid);

        Set<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new AuthResponse(token, uid, email, roles));
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("OK");
    }
}
