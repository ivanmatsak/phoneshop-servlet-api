package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class PriceRecord {
    private BigDecimal price;
    /** can be null if the price is null */
    private Currency currency;
    private String date;

    public PriceRecord(BigDecimal price, Currency currency, String date) {
        this.price = price;
        this.currency = currency;
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
