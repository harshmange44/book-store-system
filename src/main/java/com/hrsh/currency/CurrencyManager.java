package com.hrsh.currency;

import com.hrsh.enums.CurrencyCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Set;

public class CurrencyManager {

    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("USD");
    private static final Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    private final BigDecimal amount;
    private final Currency currency;

    public static CurrencyManager initCurrencyValue(BigDecimal amount, CurrencyCode currencyCode) {
        Currency currency = Currency.getInstance(currencyCode.name());
        if (availableCurrencies.contains(currency)) {
            return new CurrencyManager(amount, currency);
        }

        return new CurrencyManager(amount, DEFAULT_CURRENCY);
    }

    CurrencyManager(BigDecimal amount, Currency currency) {
        this(amount, currency, DEFAULT_ROUNDING);
    }

    CurrencyManager(BigDecimal amount, Currency currency, RoundingMode rounding) {
        this.currency = currency;
        this.amount = amount.setScale(currency.getDefaultFractionDigits(), rounding);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return getCurrency().getSymbol() + " " + getAmount();
    }
}