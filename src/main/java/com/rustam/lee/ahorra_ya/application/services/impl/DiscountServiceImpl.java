package com.rustam.lee.ahorra_ya.application.services.impl;

import com.rustam.lee.ahorra_ya.core.domain.dto.DiscountRequestDTO;
import com.rustam.lee.ahorra_ya.core.domain.entities.Bank;
import com.rustam.lee.ahorra_ya.core.domain.entities.Discount;
import com.rustam.lee.ahorra_ya.core.domain.entities.Shop;
import com.rustam.lee.ahorra_ya.core.domain.entities.UserEntity;
import com.rustam.lee.ahorra_ya.core.domain.enums.CardType;
import com.rustam.lee.ahorra_ya.core.domain.enums.DayOfWeek;
import com.rustam.lee.ahorra_ya.core.services.DiscountService;
import com.rustam.lee.ahorra_ya.infrastructure.repositories.BankRepository;
import com.rustam.lee.ahorra_ya.infrastructure.repositories.DiscountRepository;
import com.rustam.lee.ahorra_ya.infrastructure.repositories.ShopRepository;
import com.rustam.lee.ahorra_ya.infrastructure.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final BankRepository bankRepository;


    // constructor
    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository, UserRepository userRepository, ShopRepository shopRepository, BankRepository bankRepository) {
        this.discountRepository = discountRepository;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.bankRepository = bankRepository;
    }

    // methods
    @Override
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @Transactional
    @Override
    public Discount createDiscount(DiscountRequestDTO discountRequestDTO) {
        // Получаем сущности из репозиториев
        UserEntity user = userRepository.findById(discountRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Bank bank = bankRepository.findById(discountRequestDTO.getBankId())
                .orElseThrow(() -> new EntityNotFoundException("Bank not found"));

        Shop shop = shopRepository.findById(discountRequestDTO.getShopId())
                .orElseThrow(() -> new EntityNotFoundException("Shop not found"));

        Discount discount = new Discount(
                user,
                bank,
                shop,
                discountRequestDTO.getDayOfWeek(),
                discountRequestDTO.getDiscount(),
                discountRequestDTO.getDiscountLimit(),
                discountRequestDTO.getCardType(),
                discountRequestDTO.getLimitType(),
                discountRequestDTO.getDetails()
        );

        return discountRepository.save(discount);
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
    public List<Discount> getDiscountsByFilter(Optional<UUID> bankId,
                                               Optional<UUID> shopId,
                                               Optional<DayOfWeek> dayOfWeek,
                                               Optional<CardType> cardType,
                                               Optional<UUID> userId) {
        boolean includeAllDays = dayOfWeek.isPresent() && !dayOfWeek.get().equals(DayOfWeek.ALL); // Если выбран конкретный день, включаем скидки на все дни

        Specification<Discount> spec = Specification.where(DiscountSpecification.hasBankName(bankId))
                .and(DiscountSpecification.hasStoreName(shopId))
                .and(DiscountSpecification.hasDayOfWeek(dayOfWeek, includeAllDays))
                .and(DiscountSpecification.hasCardType(cardType))
                .and(DiscountSpecification.hasUserId(userId));

        System.out.println("Specification: " + spec);

        return discountRepository.findAll(spec);
    }


}
