package com.accenture.flowershop.fe.dto;

import java.util.Currency;

public class CurrencyType {

    public static Currency find(String code) {
        boolean isFound = java.util.Currency.getAvailableCurrencies().stream()
                .anyMatch(x -> x.getNumericCodeAsString().equals(code));
        return isFound ? java.util.Currency.getAvailableCurrencies().stream()
                .filter(x -> x.getNumericCodeAsString().equals(code)).findFirst().get() : null;
    }

}
