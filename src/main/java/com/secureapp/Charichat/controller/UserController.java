package com.secureapp.Charichat.controller;

import com.secureapp.Charichat.dto.UserResponse;
import com.secureapp.Charichat.entity.User;
import com.secureapp.Charichat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getPublicKey(),
                user.getCreatedAt(),
                user.getLastSeen()
        );
    }

}
