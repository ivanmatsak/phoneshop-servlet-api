package com.es.phoneshop.model.product;

import com.es.phoneshop.model.exceptions.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static ProductDao instance;

    public static synchronized ProductDao getInstance() throws ProductNotFoundException {
        if(instance==null){
            instance=new ArrayListProductDao();
        }
        return instance;
    }

    private long maxId;
    private List<Product> products;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private ArrayListProductDao(){
        this.products= new ArrayList<>();

    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException{
        lock.readLock().lock();
        try {
            return products.stream()
                    .filter(product->id.equals(product.getId()))
                    .findAny()
                    .orElseThrow(()-> new ProductNotFoundException());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        lock.readLock().lock();
        try {
            Comparator<Product> comparator=Comparator.comparing(product -> {
                if (sortField!=null && SortField.description == sortField) {
                    return (Comparable) product.getDescription();
                } else {
                    return (Comparable) product.getPrice();
                }
            });
            /*if(sortOrder==SortOrder.desc){
                comparator=comparator.thenComparing(Comparator.reverseOrder());
            }*/
            return products.stream()
                    .filter(product -> query==null || query.isEmpty() || product.getDescription().contains(query))
                    .filter(product -> product.getPrice()!=null)
                    .filter(product -> product.getStock()>0)
                    .sorted(comparator
                    )
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void save(Product product) {
        lock.writeLock().lock();

        try {
            if(product.getId()!=null){
                try{
                    Product previousProduct=getProduct(product.getId());
                    products.set(products.indexOf(previousProduct),product);
                }catch (ProductNotFoundException e){
                    product.setId(maxId++);
                    products.add(product);
                }
            }else {
                product.setId(maxId++);
                products.add(product);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(Long id) throws ProductNotFoundException {
        lock.writeLock().lock();

        try {
            products.remove(getProduct(id));
        } finally {
            lock.writeLock().unlock();
        }
    }


}
