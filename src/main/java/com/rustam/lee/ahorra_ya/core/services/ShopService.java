package com.rustam.lee.ahorra_ya.core.services;

import com.rustam.lee.ahorra_ya.core.domain.entities.Shop;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShopService {
    Optional<Shop> getShopById(UUID shopId);
    List<Shop> getAllShops();
    Shop createShop(Shop shop);
    void deleteShop(UUID shopId);
    List<Shop> searchShops(String query);
}