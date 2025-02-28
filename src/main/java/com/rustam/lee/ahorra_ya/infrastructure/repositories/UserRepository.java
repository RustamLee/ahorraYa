package com.rustam.lee.ahorra_ya.infrastructure.repositories;

import com.rustam.lee.ahorra_ya.core.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

}
