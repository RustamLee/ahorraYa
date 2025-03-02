package com.rustam.lee.ahorra_ya.infrastructure.repositories;

import com.rustam.lee.ahorra_ya.core.domain.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ShopRepository extends JpaRepository<Shop, UUID> {
    @Query("SELECT s FROM Shop s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Shop> findByNameContaining(@Param("query") String query);
}
