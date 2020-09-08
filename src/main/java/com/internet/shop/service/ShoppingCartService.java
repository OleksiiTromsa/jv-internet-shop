package com.internet.shop.service;

import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart);

    ShoppingCart getByUser(Long userId);

    boolean delete(ShoppingCart shoppingCart);
}