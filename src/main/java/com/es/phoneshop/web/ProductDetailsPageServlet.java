package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Optional;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;
    private RecentlyViewedProducts recentlyViewedProducts;
    private CartService cartService;
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        cartService= DefaultCartService.getInstance();
        recentlyViewedProducts=RecentlyViewedProducts.getInstance();
        try {
            productDao=ArrayListProductDao.getInstance();
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id=parseProductId(request);
        recentlyViewedProducts.addProduct(request,productDao.getProduct(id));
        request.setAttribute("product",productDao.getProduct(id));
        request.setAttribute("cart",cartService.getCart(request));
        request.setAttribute("viewedProducts",recentlyViewedProducts.getProducts(request));

        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId=parseProductId(request);
        String quantityString =request.getParameter("quantity");
        Cart cart=cartService.getCart(request);

        int quantity;
        try {
            NumberFormat format=NumberFormat.getInstance(request.getLocale());
            quantity= format.parse(quantityString).intValue();
        }catch (ParseException e){
            request.setAttribute("error","Not a number");
            doGet(request,response);
            return;
        }
        try {
            if (cart.containsCartItem(productDao.getProduct(productId))) {
                cartService.update(cart,productId, quantity);
            } else {
                cartService.add(cart,productId, quantity);
            }
        } catch (OutOfStockException e) {
            request.setAttribute("error","Out of stock, available "+e.getStockAvailable());
            doGet(request,response);
            return;
        }

        response.sendRedirect(request.getContextPath()+"/products/"+productId+"?message=Product added to cart");
    }

    private Long parseProductId(HttpServletRequest request){
        String productInfo=request.getPathInfo().substring(1);
        return Long.valueOf(productInfo);
    }
}
