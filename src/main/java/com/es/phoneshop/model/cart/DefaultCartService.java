package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class DefaultCartService implements CartService{
    private static final String CART_SESSION_ATTRIBUTE=DefaultCartService.class.getName()+".cart";

    private ProductDao productDao;

    private DefaultCartService(){
        productDao= ArrayListProductDao.getInstance();
    }
    private static class SingletonHelper{
        private static final DefaultCartService INSTANCE=new DefaultCartService();
    }

    public static DefaultCartService getInstance(){
        return SingletonHelper.INSTANCE;
    }
    @Override
    public synchronized Cart getCart(HttpServletRequest request) {
        Cart cart= (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if(cart==null){
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE,cart=new Cart());
        }
        return cart;
    }

    @Override
    public synchronized void add(Cart cart,Long productId, int quantity) throws OutOfStockException {
        Product product=productDao.getProduct(productId);

        checkForStock(cart,product,quantity);
        cart.getItems().add(new CartItem(product,quantity));
    }

    @Override
    public synchronized void update(Cart cart,Long productId, int quantity) throws OutOfStockException {
        Product product=productDao.getProduct(productId);
        checkForStock(cart,product,quantity);
        cart.updateCartItem(product,quantity);
    }

    public void checkForStock(Cart cart,Product product, int quantity) throws OutOfStockException {
        Optional<CartItem> item=cart.getCartItemByName(product);
        if(item.isPresent()){
            if(product.getStock()<quantity+item.get().getQuantity()){
                throw new OutOfStockException(product,product.getStock(),quantity);
            }
        }else{
            if(product.getStock()<quantity){
                throw new OutOfStockException(product,product.getStock(),quantity);
            }
        }

    }
}
