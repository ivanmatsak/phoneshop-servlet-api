package com.es.phoneshop.model.exceptions;

import com.es.phoneshop.model.product.Product;

public class OutOfStockException extends Exception{
    private Product product;
    private int stockRequested;
    private int stockAvailable;

    public Product getProduct() {
        return product;
    }

    public int getStockRequested() {
        return stockRequested;
    }

    public int getStockAvailable() {
        return stockAvailable;
    }

    public OutOfStockException(Product product, int stockAvailable, int stockRequested){
        this.product=product;
        this.stockAvailable=stockAvailable;
        this.stockRequested=stockRequested;

    }
}
