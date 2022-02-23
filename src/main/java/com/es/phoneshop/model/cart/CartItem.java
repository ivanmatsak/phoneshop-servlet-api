package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;

public class CartItem implements Serializable {
    private static final String VIRGULE=",";

    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return product.getCode() + VIRGULE + quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CartItem)) {
            return false;
        }

        CartItem c = (CartItem) o;

        return product.equals(c.product)
                && Integer.compare(quantity, c.quantity) == 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
