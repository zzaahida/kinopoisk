package com.example.kinopoisk.service;

import com.example.kinopoisk.dto.UserRegistrationDto;
import com.example.kinopoisk.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
