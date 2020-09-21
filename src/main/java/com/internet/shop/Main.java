package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

public class Main {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product nokia = new Product("Nokia", 100L);
        nokia = productService.create(nokia);
        Product nokia2 = productService.get(nokia.getId());
        System.out.println(nokia2);
        nokia.setPrice(180L);
        productService.update(nokia);
        System.out.println(nokia);

        productService.getAll().forEach(System.out::println);

        System.out.println(productService.delete(nokia.getId()));
        productService.getAll().forEach(System.out::println);
    }
}
