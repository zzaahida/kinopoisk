package com.example.kinopoisk;

import com.example.kinopoisk.dto.UserRegistrationDto;
import com.example.kinopoisk.model.Role;
import com.example.kinopoisk.model.User;
import com.example.kinopoisk.repo.UserRepo;
import com.example.kinopoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ServerInitializer implements ApplicationRunner {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        User checkUser = userRepo.findByEmail("admin@gmail.com");
        if (checkUser == null) {
            User user = new User("Admin", "admin@gmail.com", new BCryptPasswordEncoder().encode("admin"), true);
            Role role1 = new Role("ROLE_USER");
            Role role2 = new Role("ROLE_ADMIN");
            user.addRoles(role1);
            user.addRoles(role2);
            userRepo.save(user);
        }

    }
}