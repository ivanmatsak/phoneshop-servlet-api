package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void updateCartItem(Product product, int quantity) {
        CartItem item=getCartItemByName(product).get();
        int index=items.indexOf(item);
        items.set(index,new CartItem(product,item.getQuantity()+quantity));
    }
    public boolean containsCartItem(Product product){
        List<CartItem> list =items.stream().filter(o ->product.equals(o.getProduct()))
                .collect(Collectors.toList());
        return !list.isEmpty();
    }
    public Optional<CartItem> getCartItemByName(Product product){
        return items.stream().filter(o ->product.equals(o.getProduct()))
                .findFirst();
    }
    public Optional<CartItem> getCartItem(Product product, int quantity){
        return items.stream().filter(o ->o.equals(new CartItem(product,quantity)))
                .findFirst();
    }
    @Override
    public String toString() {
        return "items=[" + items + ']';
    }
}
