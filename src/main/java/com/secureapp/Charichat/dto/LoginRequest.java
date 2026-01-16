package com.secureapp.Charichat.dto;

import lombok.Data;

import java.util.random.RandomGenerator;

@Data
public class LoginRequest {
    private String email;
    private String password;

}
