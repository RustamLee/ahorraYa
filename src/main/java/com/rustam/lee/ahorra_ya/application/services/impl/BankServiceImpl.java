package com.rustam.lee.ahorra_ya.application.services.impl;


import com.rustam.lee.ahorra_ya.core.domain.entities.Bank;
import com.rustam.lee.ahorra_ya.core.services.BankService;
import com.rustam.lee.ahorra_ya.infrastructure.repositories.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    // constructor
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Optional<Bank> getBankById(UUID bankId) {
        return bankRepository.findById(bankId);
    }

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    public void deleteBank(UUID bankId) {
        bankRepository.deleteById(bankId);
    }

    @Override
    public List<Bank> searchBanks(String query) {
        return bankRepository.findByNameContaining(query);
    }
}