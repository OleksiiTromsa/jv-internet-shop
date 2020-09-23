package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.UserService;

import java.util.Set;

public class Main {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService =
                (ProductService) injector.getInstance(ProductService.class);

//        Product nokia = new Product("Nokia", 100L);
//        nokia = productService.create(nokia);
//        Product nokia2 = productService.get(nokia.getId());
//        System.out.println(nokia2);
//        nokia.setPrice(180L);
//        productService.update(nokia);
//        System.out.println(nokia);
//
//        productService.getAll().forEach(System.out::println);
//
//        System.out.println(productService.delete(nokia.getId()));
//        productService.getAll().forEach(System.out::println);

        UserService userService =
                (UserService) injector.getInstance(UserService.class);
//        User bob = new User("Bob", "Bob", "1");
//        bob.setRoles(Set.of(Role.of("USER")));
//        bob = userService.create(bob);
//
//        User alisa = new User("Alisa", "Alisa", "1");
//        alisa.setRoles(Set.of(Role.of("USER")));
//        alisa = userService.create(alisa);
//
//        User admin = new User("admin", "admin", "1");
//        admin.setRoles(Set.of(Role.of("ADMIN")));
//        admin = userService.create(admin);
//
//        bob.setName("Robert");
//        userService.update(bob);
//
//        User newUser = userService.get(1L);
//        System.out.println(newUser);
//
//        userService.delete(2L);
//        System.out.println(userService.getAll());

        userService.delete(1L);
        userService.delete(3L);
        userService.delete(4L);
        userService.delete(5L);
        userService.delete(6L);
    }
}
