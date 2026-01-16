package com.secureapp.Charichat.service;

import com.secureapp.Charichat.entity.User;
import com.secureapp.Charichat.repository.UserRepository;
import com.secureapp.Charichat.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User register(String email, String password, String displayName, String publicKey) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .displayName(displayName)
                .publicKey(publicKey)
                .build();

        return userRepository.save(user);
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getId(), user.getEmail());
    }
}
