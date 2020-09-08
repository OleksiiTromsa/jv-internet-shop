package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;

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
        System.out.println("Create 3 products");
        System.out.println(productService.getAll());
        Product iphoneX = new Product(productService.get(3L));
        iphoneX.setPrice(800L);
        productService.update(iphoneX);
        productService.delete(1L);
        System.out.println("Change iphone, delete samsung");
        System.out.println(productService.getAll());

        UserService userService = (UserService) injector.getInstance(UserService.class);

        User bob = new User("Bob", "BobOdessa2002", "1234");
        User mark = new User("Mark", "MarkKrasav4eg", "322223");
        userService.create(bob);
        userService.create(mark);
        System.out.println("Create 2 users");
        System.out.println(userService.getAll());
        User robert = new User("Robert", "BobOdessa2002", "1234");
        robert.setId(1L);
        userService.update(robert);
        userService.delete(2L);
        System.out.println("Delete Mark, update Bob");
        System.out.println(userService.getAll());

        ShoppingCartService shoppingCartService = (ShoppingCartService)
                injector.getInstance(ShoppingCartService.class);

        ShoppingCart robertShoppingCart = shoppingCartService.getByUser(robert.getId());
        shoppingCartService.addProduct(robertShoppingCart, xiaomi);
        shoppingCartService.addProduct(robertShoppingCart, iphoneX);
        System.out.println("Add 2 items to Robert's shopping cart");
        System.out.println(shoppingCartService.getByUser(robert.getId()).getProducts());
        shoppingCartService.deleteProduct(robertShoppingCart, iphoneX);
        System.out.println("Delete iphone from Robert's shopping cart");
        System.out.println(shoppingCartService.getByUser(robert.getId()).getProducts());

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);

        shoppingCartService.addProduct(robertShoppingCart, xiaomi);
        Order robertFirstOrder = orderService.completeOrder(robertShoppingCart);
        shoppingCartService.addProduct(robertShoppingCart, iphoneX);
        Order robertSecondOrder = orderService.completeOrder(robertShoppingCart);
        System.out.println("Create 2 Robert's orders");
        System.out.println(orderService.getUserOrders(robert.getId()));
        orderService.delete(1L);
        System.out.println("Delete one of them");
        System.out.println(orderService.getUserOrders(robert.getId()));
    }
}
