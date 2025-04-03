package com.example.airBnb.demo.strategy;

import com.example.airBnb.demo.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class HolidayPricingStrategy implements PricingStrategy {

    private final PricingStrategy wrapped;

    public HolidayPricingStrategy(PricingStrategy wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        boolean isTodayHoliday = true; //Call an API or check with local data

        if(isTodayHoliday)
        {
            price = price.multiply(BigDecimal.valueOf(1.25));
        }
        return price;
    }
}
