package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.NegativeQuantityException;
import com.es.phoneshop.model.exceptions.OutOfStockException;

import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);

    void add(Cart cart, Long productId, int quantity) throws OutOfStockException, NegativeQuantityException;

    void update(Cart cart, Long productId, int quantity) throws OutOfStockException, NegativeQuantityException;

    void delete(Cart cart, Long productId);

    void clearCart(Cart cart);
}
