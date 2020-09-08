package com.internet.shop.dao.impl;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.ShoppingCart;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> getUserShoppingCard(Long userId) {
        return Storage.shoppingCarts.stream()
                      .filter(shoppingCart -> shoppingCart.getUserId().equals(userId))
                      .findFirst();
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().clear();
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        IntStream.range(0, Storage.shoppingCarts.size())
                 .filter(i -> Storage.shoppingCarts.get(i).getId().equals(shoppingCart.getId()))
                 .findFirst()
                 .ifPresentOrElse(i -> Storage.shoppingCarts.set(i, shoppingCart), () -> {
                     throw new IllegalArgumentException("No shopping cart with id "
                        + shoppingCart.getId() + " in storage");
                 });
        return shoppingCart;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.shoppingCarts.removeIf(shoppingCart ->
                shoppingCart.getId().equals(id));
    }
}