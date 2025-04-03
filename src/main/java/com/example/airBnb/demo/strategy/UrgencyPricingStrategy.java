package com.example.airBnb.demo.strategy;

import com.example.airBnb.demo.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UrgencyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;


    public UrgencyPricingStrategy(PricingStrategy wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        LocalDate today = LocalDate.now();
        if(!inventory.getDate().isBefore(today) && inventory.getDate().isBefore(today.plusDays(7)))
        {
            price = price.multiply(BigDecimal.valueOf(1.15));
        }
        return price;
    }
}
