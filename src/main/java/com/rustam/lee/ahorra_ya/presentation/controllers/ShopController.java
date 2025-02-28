package com.rustam.lee.ahorra_ya.presentation.controllers;


import com.rustam.lee.ahorra_ya.application.services.impl.ShopServiceImpl;
import com.rustam.lee.ahorra_ya.core.domain.entities.Shop;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Shop>> getAllShops(){
        List<Shop> shops = shopService.getAllShops();
        return ResponseEntity.ok(shops);
    }


}
