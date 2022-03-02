package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.exceptions.*;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AdvancedSearchPageServlet extends HttpServlet {
    private static final String JSP_PATH = "/WEB-INF/pages/advancedSearchPage.jsp";

    private ProductDao productDao;
    private String message = "";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            productDao = ArrayListProductDao.getInstance();
        } catch (ProductNotFoundException e) {
            message = "Product not found";
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productCode = request.getParameter("productCode");
        String maxPrice = request.getParameter("maxPrice");
        String minPrice = request.getParameter("minPrice");
        String minStock = request.getParameter("minStock");
        String message = "Product finded successfully";
        request.setAttribute("error", message);

        if( !maxPrice.isEmpty() && !minPrice.isEmpty() && !minStock.isEmpty() && !productCode.isEmpty()){
            request.setAttribute("products", productDao.searchProducts(productCode, maxPrice, minPrice, minStock));
        }

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }
}
