package com.rustam.lee.ahorra_ya.presentation.controllers;

import com.rustam.lee.ahorra_ya.core.domain.entities.Bank;
import com.rustam.lee.ahorra_ya.core.domain.entities.Discount;
import com.rustam.lee.ahorra_ya.core.domain.entities.Shop;
import com.rustam.lee.ahorra_ya.core.domain.entities.User;
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
import java.util.stream.Collectors;

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
    @PostMapping
    public ResponseEntity<Discount> createDiscount(
            @RequestParam UUID userId,
            @RequestParam UUID bankId,
            @RequestParam UUID shopId,
            @RequestBody Discount discount) {

        Optional<User> user = userService.getUserById(userId);
        Optional<Bank> bank = bankService.getBankById(bankId);
        Optional<Shop> shop = shopService.getShopById(shopId);

        if (user.isEmpty() || bank.isEmpty() || shop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        discount.setUser(user.get());
        discount.setBank(bank.get());
        discount.setShop(shop.get());

        Discount createdDiscount = discountService.createDiscount(discount);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
    }

    // delete discount
    @DeleteMapping("/{discountId}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable UUID discountId){
        discountService.deleteDiscount(discountId);
        return ResponseEntity.noContent().build();
    }

    // get discount by id
    @GetMapping("/{discountId}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable UUID discountId) {
        Discount discount = discountService.getDiscountById(discountId);

        if (discount != null) {
            return ResponseEntity.ok(discount);  // Возвращаем 200 OK с дисконтом в теле
        } else {
            return ResponseEntity.notFound().build();  // Возвращаем 404 Not Found
        }
    }

    // update discount
    @PutMapping("/{discountId}")
    public ResponseEntity<Discount> updateDiscount(
            @PathVariable UUID discountId,
            @RequestBody Discount updatedDiscount) {

        Discount updated = discountService.updateDiscount(discountId, updatedDiscount);
        return ResponseEntity.ok(updated);
    }


    // get discounts by filter  (bankName, storeName, dayOfWeek, cardType, userId)
    @GetMapping ("/filter")
    public ResponseEntity<List<Discount>> getDiscountsByFilter(
            @RequestParam Optional<UUID> bankId,
            @RequestParam Optional<UUID> shopId,
            @RequestParam Optional<DayOfWeek> dayOfWeek,
            @RequestParam Optional<CardType> cardType,
            @RequestParam Optional<UUID> userId) {
        System.out.println("Received parameters: ");
        System.out.println("bankName: " + bankId);
        System.out.println("shopId: " + shopId);
        System.out.println("dayOfWeek: " + dayOfWeek);
        System.out.println("cardType: " + cardType);
        System.out.println("userId: " + userId);

        List<Discount> discounts = discountService.getDiscountsByFilter(bankId, shopId, dayOfWeek, cardType, userId);
        return ResponseEntity.ok(discounts);
    }

}
