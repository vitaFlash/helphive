package com.hive.help.security;

import com.hive.help.model.User;
import com.hive.help.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = users.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return new CustomUserPrincipal(
                u.getId(),
                u.getEmail(),
                u.getPassword(),
                Boolean.TRUE.equals(u.getActive()), // you renamed to getActive()
                List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name().replace("ROLE_", "")))
        );
    }
}
