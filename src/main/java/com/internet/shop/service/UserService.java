package com.internet.shop.service;

import com.internet.shop.model.User;

public interface UserService extends GenericService<User, Long> {
    User create(User user);

    User update(User user);
}
