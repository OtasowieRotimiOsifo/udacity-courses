package com.udacity.pricing.service;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;



/**
 * Implements the pricing service to get prices for each vehicle.
 */
public class PricingService {

    /**
     * Holds {ID: Price} pairings (current implementation allows for 25 vehicles)
     */
    public static final List<Price> PRICES = LongStream
            .range(1, 26)
            .mapToObj(i -> new Price(null, "USD", randomPrice(), i))
            .collect(Collectors.toList());

    /**
     * Gets a random price to fill in for a given vehicle ID.
     * @return random price for a vehicle
     */
    private static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

}
