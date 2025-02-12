package com.rustam.lee.ahorra_ya.infrastructure.repositories;

import com.rustam.lee.ahorra_ya.core.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
