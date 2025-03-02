package com.rustam.lee.ahorra_ya.application.services.impl;

import com.rustam.lee.ahorra_ya.core.domain.entities.Shop;
import com.rustam.lee.ahorra_ya.core.services.ShopService;
import com.rustam.lee.ahorra_ya.infrastructure.repositories.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Optional<Shop> getShopById(UUID shopId) {
        return shopRepository.findById(shopId);
    }

    @Override
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public Shop createShop(Shop shop) {
        return shopRepository.save(shop);
    }

    @Override
    public void deleteShop(UUID shopId) {
        shopRepository.deleteById(shopId);
    }

    @Override
    public List<Shop> searchShops(String query) {
        return shopRepository.findByNameContaining(query);
    }
}
