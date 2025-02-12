package com.rustam.lee.ahorra_ya.presentation.controllers;


import com.rustam.lee.ahorra_ya.application.services.impl.ShopServiceImpl;
import com.rustam.lee.ahorra_ya.core.domain.entities.Shop;
import com.rustam.lee.ahorra_ya.core.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shops")
public class ShopController {

    private final ShopServiceImpl shopService;

    // constructor
    public ShopController(ShopServiceImpl shopService) {
        this.shopService = shopService;
    }

    // methods
    @PostMapping
    public ResponseEntity<Shop > createShop(@RequestBody Shop shop) {
        Shop saveShop = shopService.createShop(shop);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveShop);
    }


}
