package com.example.kinopoisk;

import com.example.kinopoisk.model.Role;
import com.example.kinopoisk.model.User;
import com.example.kinopoisk.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@SpringBootApplication
public class KinopoiskApplication {

	public static void main(String[] args) {
		SpringApplication.run(KinopoiskApplication.class, args);
	}
}

