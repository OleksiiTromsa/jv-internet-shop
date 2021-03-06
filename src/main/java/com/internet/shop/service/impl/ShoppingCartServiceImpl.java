package com.internet.shop.service.impl;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.service.ShoppingCartService;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartDao.create(shoppingCart);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().clear();
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(product);
        return shoppingCartDao.update(shoppingCart);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        boolean result = shoppingCart.getProducts().remove(product);
        if (result) {
            shoppingCartDao.update(shoppingCart);
        }
        return result;
    }

    @Override
    public ShoppingCart getByUser(Long userId) {
        return shoppingCartDao.getUserShoppingCart(userId).orElseThrow(() ->
                new IllegalArgumentException("Can't get shopping card of user with id "
                    + userId));
    }

    @Override
    public boolean delete(Long id) {
        return shoppingCartDao.delete(id);
    }

    @Override
    public ShoppingCart get(Long id) {
        return shoppingCartDao.get(id).orElseThrow(() ->
                new IllegalArgumentException("No shopping cart with id " + id));
    }

    @Override
    public List<ShoppingCart> getAll() {
        return shoppingCartDao.getAll();
    }
}
