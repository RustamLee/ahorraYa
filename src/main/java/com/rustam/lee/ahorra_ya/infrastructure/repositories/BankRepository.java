package com.rustam.lee.ahorra_ya.infrastructure.repositories;

import com.rustam.lee.ahorra_ya.core.domain.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {
    @Query("SELECT b FROM Bank b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Bank> findByNameContaining(@Param("query") String query);
}
