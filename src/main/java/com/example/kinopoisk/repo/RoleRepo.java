package com.example.kinopoisk.repo;

import com.example.kinopoisk.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
