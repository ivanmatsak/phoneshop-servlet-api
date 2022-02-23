package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCartItemServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private Product product;
    @Mock
    protected ServletConfig config;


    DeleteCartItemServlet servlet;

    @Before
    public void setup() throws ServletException {
        servlet = new DeleteCartItemServlet();
        servlet.init(config);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getPathInfo()).thenReturn("/2");
        when(product.getId()).thenReturn((long) 2);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        ArrayListProductDao.getInstance().save(product);
        servlet.doPost(request, response);
        verify(request).setAttribute(anyString(), any());
        verify(request.getRequestDispatcher(anyString())).forward(request, response);
    }
}
