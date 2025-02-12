package com.rustam.lee.ahorra_ya.presentation.controllers;

import com.rustam.lee.ahorra_ya.application.services.impl.BankServiceImpl;
import com.rustam.lee.ahorra_ya.core.domain.entities.Bank;
import com.rustam.lee.ahorra_ya.core.services.BankService;
import com.rustam.lee.ahorra_ya.infrastructure.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banks")
public class BankController {
    private final BankServiceImpl bankService;

    // constructor
    @Autowired
    public BankController(BankRepository bankRepository, BankServiceImpl bankService) {
        this.bankService = bankService;
    }


    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        Bank savedBank = bankService.createBank(bank);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBank);
    }



}
