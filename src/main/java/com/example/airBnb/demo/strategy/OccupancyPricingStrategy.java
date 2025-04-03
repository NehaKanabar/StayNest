package com.example.airBnb.demo.strategy;

import com.example.airBnb.demo.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public class OccupancyPricingStrategy implements PricingStrategy {
    private final PricingStrategy wrapped;

    public OccupancyPricingStrategy(PricingStrategy wrapped) {
        this.wrapped = wrapped;
    }


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);

        double occupancyRate = (double) inventory.getBookedCount() / inventory.getTotalCount();
        if(occupancyRate > 0.8)
        {
            price = price.multiply(BigDecimal.valueOf(1.2));
        }
        return price;
    }
}
