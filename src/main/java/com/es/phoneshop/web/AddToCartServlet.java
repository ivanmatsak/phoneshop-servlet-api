package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.exceptions.NegativeQuantityException;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddToCartServlet extends HttpServlet {
    private CartService cartService;
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Long, String> errors = new HashMap<>();
        Long productId = Long.valueOf(request.getPathInfo().substring(1));
        String[] quantities = request.getParameterValues("quantity");
        try {
            cartService.update(cartService.getCart(request.getSession()),productId, Integer.valueOf(quantities[Math.toIntExact(productId)]));
        } catch (OutOfStockException | NegativeQuantityException e) {
            handleError(errors, productId, e, request);
        }
        if (errors.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products?message=Cart updated successfully");
        } else {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }

        private int getQuantity(HttpServletRequest request, String quantityString) throws ParseException {
            NumberFormat numberFormat = getNumberFormat(request.getLocale());
            return numberFormat.parse(quantityString).intValue();
        }

        protected NumberFormat getNumberFormat (Locale locale){
            return NumberFormat.getInstance(locale);
        }

        private void handleError (Map < Long, String > errors, Long productId, Exception exception, HttpServletRequest
        request){
            if (exception.getClass().equals(ParseException.class)) {
                errors.put(productId, "Not a number");
            } else if (exception.getClass().equals(NegativeQuantityException.class)) {
                errors.put(productId, "Negative quantity");
            } else if (exception.getClass().equals(OutOfStockException.class)) {
                Product product = productDao.getProduct(productId);
                int inStock = product.getStock();
                errors.put(productId, "Out of stock, max available " + inStock);
            }
        }
    }
