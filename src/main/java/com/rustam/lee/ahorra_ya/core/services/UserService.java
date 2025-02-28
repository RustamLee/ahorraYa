package com.rustam.lee.ahorra_ya.core.services;

import com.rustam.lee.ahorra_ya.core.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<UserEntity> getUserById(UUID userId);
    List<UserEntity> getAllUsers();
    UserEntity createUser(UserEntity user);
    void deleteUser(UUID userId);
}