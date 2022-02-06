package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class PriceRecord {
    private BigDecimal price;
    /** can be null if the price is null */
    private Currency currency;
    private SimpleDateFormat date;

    public PriceRecord(BigDecimal price, Currency currency, SimpleDateFormat date) {
        this.price = price;
        this.currency = currency;
        this.date=date;
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

    public String getStringDate(){
        return date.format(new Date());
    }
    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }
}
