package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.Role;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/inject")
public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User testUser = new User("bob", "bob", "1");
        testUser.setRoles(Set.of(Role.of("USER")));
        userService.create(testUser);
        shoppingCartService.create(new ShoppingCart(testUser.getId()));

        User admin = new User("admin", "admin", "1");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(admin);
        shoppingCartService.create(new ShoppingCart(admin.getId()));

        productService.create(new Product("Xiaomi", 200L));
        productService.create(new Product("Samsung", 400L));
        productService.create(new Product("Iphone", 600L));

        resp.sendRedirect(req.getContextPath() + "/users/login");
    }
}
