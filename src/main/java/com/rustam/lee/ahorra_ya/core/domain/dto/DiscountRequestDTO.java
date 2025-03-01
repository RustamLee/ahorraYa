package com.rustam.lee.ahorra_ya.core.domain.dto;

import com.rustam.lee.ahorra_ya.core.domain.enums.CardType;
import com.rustam.lee.ahorra_ya.core.domain.enums.DayOfWeek;
import com.rustam.lee.ahorra_ya.core.domain.enums.LimitType;

import java.util.UUID;

public class DiscountRequestDTO {
    private UUID userId;
    private UUID bankId;
    private UUID shopId;
    private DayOfWeek dayOfWeek;
    private double discount;
    private double discountLimit;
    private CardType cardType;
    private LimitType limitType;
    private String details;

    // Геттеры и сеттеры
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getBankId() {
        return bankId;
    }

    public void setBankId(UUID bankId) {
        this.bankId = bankId;
    }

    public UUID getShopId() {
        return shopId;
    }

    public void setShopId(UUID shopId) {
        this.shopId = shopId;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getDiscountLimit() {
        return discountLimit;
    }

    public void setDiscountLimit(int discountLimit) {
        this.discountLimit = discountLimit;
    }

    public LimitType getLimitType() {
        return limitType;
    }

    public void setLimitType(LimitType limitType) {
        this.limitType = limitType;
    }
}
