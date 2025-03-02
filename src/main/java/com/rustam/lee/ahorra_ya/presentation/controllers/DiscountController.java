package com.rustam.lee.ahorra_ya.presentation.controllers;

import com.rustam.lee.ahorra_ya.core.domain.dto.DiscountRequestDTO;
import com.rustam.lee.ahorra_ya.core.domain.entities.Bank;
import com.rustam.lee.ahorra_ya.core.domain.entities.Discount;
import com.rustam.lee.ahorra_ya.core.domain.entities.Shop;
import com.rustam.lee.ahorra_ya.core.domain.entities.UserEntity;
import com.rustam.lee.ahorra_ya.core.domain.enums.CardType;
import com.rustam.lee.ahorra_ya.core.domain.enums.DayOfWeek;
import com.rustam.lee.ahorra_ya.core.services.BankService;
import com.rustam.lee.ahorra_ya.core.services.DiscountService;
import com.rustam.lee.ahorra_ya.core.services.ShopService;
import com.rustam.lee.ahorra_ya.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService discountService;
    private final UserService userService;
    private final BankService bankService;
    private final ShopService shopService;

    // constructor
    @Autowired
    public DiscountController(DiscountService discountService, UserService userService, BankService bankService, ShopService shopService) {
        this.discountService = discountService;
        this.userService = userService;
        this.bankService = bankService;
        this.shopService = shopService;
    }

    // methods

    // get all discounts
    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts(){
        List<Discount> discounts = discountService.getAllDiscounts();
       return ResponseEntity.ok(discounts);
    }

    // create discount
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Discount createDiscount(@RequestBody DiscountRequestDTO discountRequestDTO) {
        return discountService.createDiscount(discountRequestDTO);
    }

    // delete discount
    @DeleteMapping("delete/{discountId}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable UUID discountId){
        discountService.deleteDiscount(discountId);
        return ResponseEntity.noContent().build();
    }

    // get discount by id
    @GetMapping("get/{discountId}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable UUID discountId) {
        Discount discount = discountService.getDiscountById(discountId);

        if (discount != null) {
            return ResponseEntity.ok(discount);  // Возвращаем 200 OK с дисконтом в теле
        } else {
            return ResponseEntity.notFound().build();  // Возвращаем 404 Not Found
        }
    }

    // update discount
    @PutMapping("update/{discountId}")
    public ResponseEntity<Discount> updateDiscount(
            @PathVariable UUID discountId,
            @RequestBody Discount updatedDiscount) {

        Discount updated = discountService.updateDiscount(discountId, updatedDiscount);
        return ResponseEntity.ok(updated);
    }

    // get discounts by filter  (bankName, storeName, dayOfWeek, cardType, userId)
    @GetMapping("/filter")
    public ResponseEntity<List<Discount>> getDiscountsByFilter(
            @RequestParam Optional<UUID> bankId,
            @RequestParam Optional<UUID> shopId,
            @RequestParam Optional<DayOfWeek> dayOfWeek,
            @RequestParam Optional<CardType> cardType,
            @RequestParam Optional<UUID> userId) {

        List<Discount> discounts = discountService.getDiscountsByFilter(bankId, shopId, dayOfWeek, cardType, userId);
        return ResponseEntity.ok(discounts);
    }


}
