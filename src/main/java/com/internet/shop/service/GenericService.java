package com.internet.shop.service;

import java.util.List;

public interface GenericService<T, K> {
    T get(K id);

    List<T> getAll();

    boolean delete(K id);
}
