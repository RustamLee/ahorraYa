package com.rustam.lee.ahorra_ya.core.services;

import com.rustam.lee.ahorra_ya.core.domain.entities.Bank;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankService {
    Optional<Bank> getBankById(UUID bankId);
    List<Bank> getAllBanks();
    Bank createBank(Bank bank);
    void deleteBank(UUID bankId);
    List<Bank> searchBanks(String query);
}