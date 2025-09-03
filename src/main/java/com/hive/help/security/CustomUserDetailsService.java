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
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if (!u.getActive()) {
            throw new UsernameNotFoundException("User inactive: " + email);
        }

        List<SimpleGrantedAuthority> auth = List.of(new SimpleGrantedAuthority(u.getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                u.getEmail(), u.getPassword(), auth
        );
    }
}
