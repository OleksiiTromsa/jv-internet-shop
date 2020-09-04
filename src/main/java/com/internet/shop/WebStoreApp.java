package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

public class WebStoreApp {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product xiaomi = new Product("Xiaomi", 200L);
        Product samsung = new Product("Samsung", 400L);
        Product iphone = new Product("Iphone", 600L);

        productService.create(xiaomi);
        productService.create(samsung);
        productService.create(iphone);
        System.out.println(productService.getAll());

        Product iphoneX = new Product(productService.get(3L));
        iphoneX.setPrice(800L);
        productService.update(iphoneX);
        productService.delete(1L);
        System.out.println(productService.getAll());
    }
}
