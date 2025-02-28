package com.rustam.lee.ahorra_ya.presentation.controllers;

import com.rustam.lee.ahorra_ya.core.domain.entities.UserEntity;
import com.rustam.lee.ahorra_ya.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        System.out.println("Received request to create user: " + user);
        UserEntity savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

}
