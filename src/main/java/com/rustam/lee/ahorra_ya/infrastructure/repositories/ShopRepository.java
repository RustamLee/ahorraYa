package com.rustam.lee.ahorra_ya.infrastructure.repositories;

import com.rustam.lee.ahorra_ya.core.domain.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShopRepository extends JpaRepository<Shop, UUID> {
}
