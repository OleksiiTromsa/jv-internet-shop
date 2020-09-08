package com.internet.shop.service.impl;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.dao.UserDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public User create(User user) {
        userDao.create(user);
        shoppingCartDao.create(new ShoppingCart(user.getId()));
        return user;
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).orElseThrow(() ->
                new IllegalArgumentException("No user with id " + id));
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public Boolean delete(Long id) {
        return userDao.delete(id);
    }
}
