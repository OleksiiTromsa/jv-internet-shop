package com.internet.shop.dao;

import com.internet.shop.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getUserShoppingCard(Long userId);

    ShoppingCart update(ShoppingCart shoppingCart);

    boolean delete(Long id);
}
