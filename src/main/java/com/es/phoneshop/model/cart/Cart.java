package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cart implements Serializable {
    private List<CartItem> items;

    private int totalQuantity;
    private BigDecimal totalCost;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public boolean containsCartItem(Product product) {
        List<CartItem> list = items.stream()
                .filter(o -> product.equals(o.getProduct()))
                .collect(Collectors.toList());
        return !list.isEmpty();
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Optional<CartItem> getCartItemByName(Product product) {
        return items.stream()
                .filter(o -> product.equals(o.getProduct()))
                .findFirst();
    }

    public Optional<CartItem> getCartItem(Product product, int quantity) {
        return items.stream()
                .filter(o -> o.equals(new CartItem(product, quantity)))
                .findFirst();
    }

    @Override
    public String toString() {
        return "items=[" + items + ']';
    }
}
