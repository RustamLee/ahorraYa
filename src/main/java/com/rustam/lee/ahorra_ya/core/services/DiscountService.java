package com.rustam.lee.ahorra_ya.core.services;

import com.rustam.lee.ahorra_ya.core.domain.dto.DiscountRequestDTO;
import com.rustam.lee.ahorra_ya.core.domain.entities.Discount;
import com.rustam.lee.ahorra_ya.core.domain.enums.CardType;
import com.rustam.lee.ahorra_ya.core.domain.enums.DayOfWeek;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiscountService {

    List<Discount> getAllDiscounts();
    Discount createDiscount(DiscountRequestDTO discountRequestDTO);
    void deleteDiscount(UUID discountId);
    Discount getDiscountById(UUID discountId);
    Discount updateDiscount(UUID discountId, Discount discount);

    List<Discount> getDiscountsByFilter(Optional<UUID> bankId,
                                        Optional<UUID> shopId,
                                        Optional<DayOfWeek> dayOfWeek,
                                        Optional<CardType> cardType,
                                        Optional<UUID> userId);
}
