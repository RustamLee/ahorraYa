package com.rustam.lee.ahorra_ya.infrastructure.specification;

import com.rustam.lee.ahorra_ya.core.domain.entities.Discount;
import com.rustam.lee.ahorra_ya.core.domain.enums.CardType;
import com.rustam.lee.ahorra_ya.core.domain.enums.DayOfWeek;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public class DiscountSpecification {

    public static Specification<Discount> hasBankName(Optional<UUID> bankId) {
        return (root, query, criteriaBuilder) -> bankId.map(id ->
                criteriaBuilder.equal(root.get("bank").get("id"), id)
        ).orElse(null);
    }

    public static Specification<Discount> hasStoreName(Optional<UUID> shopId) {
        return (root, query, criteriaBuilder) -> shopId.map(id ->
                criteriaBuilder.equal(root.get("shop").get("id"), id)
        ).orElse(null);
    }


    public static Specification<Discount> hasDayOfWeek(Optional<DayOfWeek> dayOfWeek, boolean includeAllDays) {
        return (root, query, criteriaBuilder) -> {
            if (dayOfWeek.isPresent()) {
                System.out.println("Applying filter for dayOfWeek: " + dayOfWeek.get());
                if (includeAllDays) {
                    return criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("dayOfWeek"), dayOfWeek.get()),
                            criteriaBuilder.equal(root.get("dayOfWeek"), "ALL")
                    );
                } else {
                    return criteriaBuilder.equal(root.get("dayOfWeek"), dayOfWeek.get());
                }
            } else {
                return criteriaBuilder.conjunction(); // Возвращаем conjunction вместо null
            }
        };
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
