package com.rustam.lee.ahorra_ya.infrastructure.specification;

import com.rustam.lee.ahorra_ya.core.domain.entities.Discount;
import com.rustam.lee.ahorra_ya.core.domain.enums.CardType;
import com.rustam.lee.ahorra_ya.core.domain.enums.DayOfWeek;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public class DiscountSpecification {

    public static Specification<Discount> hasBankName(Optional<String> bankName) {
        return (root, query, criteriaBuilder) -> bankName.map(name ->
                criteriaBuilder.equal(root.get("bank").get("name"), name)
        ).orElse(null);
    }

    public static Specification<Discount> hasStoreName(Optional<String> storeName) {
        return (root, query, criteriaBuilder) -> storeName.map(name ->
                criteriaBuilder.equal(root.get("shop").get("name"), name)
        ).orElse(null);
    }

    public static Specification<Discount> hasDayOfWeek(Optional<DayOfWeek> dayOfWeek) {
        return (root, query, criteriaBuilder) -> dayOfWeek.map(day ->
                criteriaBuilder.equal(root.get("dayOfWeek"), day)
        ).orElse(null);
    }

    public static Specification<Discount> hasCardType(Optional<CardType> cardType) {
        return (root, query, criteriaBuilder) -> cardType.map(type ->
                criteriaBuilder.equal(root.get("cardType"), type)
        ).orElse(null);
    }

    public static Specification<Discount> hasUserId(Optional<UUID> userId) {
        return (root, query, criteriaBuilder) -> userId.map(id ->
                criteriaBuilder.equal(root.get("user").get("id"), id)
        ).orElse(null);
    }
}
