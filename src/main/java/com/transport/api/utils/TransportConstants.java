package com.transport.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class TransportConstants {

    public static BigDecimal TAX;

    @Value("${com.transport.tax}")
    public void setTax(final BigDecimal tax) {
        TAX = tax;
    }

    public static short FUEL_CONSUMPTION;

    @Value("${com.transport.fuel-consumption}")
    public void setFuelConsumption(final short fuelConsumption) {
        FUEL_CONSUMPTION = fuelConsumption;
    }

    public static BigDecimal FUEL_COST;

    @Value("${com.transport.fuel-cost}")
    public void setFuelCost(final BigDecimal fuelCost) {
        FUEL_COST = fuelCost;
    }
}
