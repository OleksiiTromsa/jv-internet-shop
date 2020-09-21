package com.internet.shop;

import com.internet.shop.dao.jdbc.ProductDaoJdbcImpl;
import com.internet.shop.model.Product;

public class Main {
    public static void main(String[] args) {
        ProductDaoJdbcImpl productDao = new ProductDaoJdbcImpl();
        Product nokia = new Product("Nokia", 100L);
        nokia = productDao.create(nokia);
        Product nokia2 = productDao.get(nokia.getId()).get();
        System.out.println(nokia2);
        nokia.setPrice(180L);
        productDao.update(nokia);
        System.out.println(nokia);

        productDao.getAll().forEach(System.out::println);

        System.out.println(productDao.delete(nokia.getId()));
        productDao.getAll().forEach(System.out::println);
    }
}
