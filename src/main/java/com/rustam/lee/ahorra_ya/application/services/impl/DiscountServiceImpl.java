package com.rustam.lee.ahorra_ya.application.services.impl;

import com.rustam.lee.ahorra_ya.core.domain.entities.Discount;
import com.rustam.lee.ahorra_ya.core.domain.enums.CardType;
import com.rustam.lee.ahorra_ya.core.domain.enums.DayOfWeek;
import com.rustam.lee.ahorra_ya.core.services.DiscountService;
import com.rustam.lee.ahorra_ya.infrastructure.repositories.DiscountRepository;
import com.rustam.lee.ahorra_ya.infrastructure.specification.DiscountSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    // constructor
    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    // methods
    @Override
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @Transactional
    @Override
    public Discount createDiscount(Discount discount) {
        if (discount.getId() != null) {
            throw new IllegalArgumentException("Discount already has Id! ");
        }
        if(discount.getDayOfWeek()==null){
            throw new IllegalArgumentException("Day of week is required");
        }
        if (discount.getDiscount() <= 0) {
            throw new IllegalArgumentException("Discount must be greater than 0");
        }
        if(discount.getDiscountLimit() <= 0){
            throw new IllegalArgumentException("Discount limit must be greater than 0");
        }
        if(discount.getCardType()==null){
            throw new IllegalArgumentException("DiscountCard type is required");
        }
        if (discount.getLimitType() == null) {
            throw new IllegalArgumentException("Limit type is required");
        }
        Discount discountToSave = new Discount(
                discount.getUser(),
                discount.getBank(),
                discount.getShop(),
                discount.getDayOfWeek(),
                discount.getDiscount(),
                discount.getDiscountLimit(),
                discount.getCardType(),
                discount.getLimitType(),
                discount.getDetails()
        );
        return discountRepository.save(discountToSave);
    }

    @Override
    public void deleteDiscount(UUID discountId) {
        if (!discountRepository.existsById(discountId)) {
            throw new EntityNotFoundException("Discount with ID " + discountId + " not found.");
        }
        discountRepository.deleteById(discountId);
    }

    @Override
    public Discount getDiscountById(UUID discountId) {
        return discountRepository.findById(discountId).orElseThrow(() -> new EntityNotFoundException("Discount with ID " + discountId + " not found."));    }

    @Override
    public Discount updateDiscount(UUID discountId, Discount updatedDiscount) {
        Discount existingDiscount = discountRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found"));
        existingDiscount.setDayOfWeek(updatedDiscount.getDayOfWeek());
        existingDiscount.setDiscount(updatedDiscount.getDiscount());
        existingDiscount.setDiscountLimit(updatedDiscount.getDiscountLimit());
        existingDiscount.setCardType(updatedDiscount.getCardType());
        existingDiscount.setLimitType(updatedDiscount.getLimitType());
        existingDiscount.setDetails(updatedDiscount.getDetails());
        return discountRepository.save(existingDiscount);
    }

    @Override
    public List<Discount> getDiscountsByFilter(Optional<String> bankName,
                                               Optional<UUID> shopId,
                                               Optional<DayOfWeek> dayOfWeek,
                                               Optional<CardType> cardType,
                                               Optional<UUID> userId) {
        Specification<Discount> spec = Specification.where(DiscountSpecification.hasBankName(bankName))
                .and(DiscountSpecification.hasStoreName(shopId))
                .and(DiscountSpecification.hasDayOfWeek(dayOfWeek))
                .and(DiscountSpecification.hasCardType(cardType))
                .and(DiscountSpecification.hasUserId(userId));
        System.out.println("Specification: " + spec);

        return discountRepository.findAll(spec);
    }

}
