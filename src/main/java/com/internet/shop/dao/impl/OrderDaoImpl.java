package com.internet.shop.dao.impl;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order createOrder(Order order) {
        Storage.addOrder(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orders.stream()
                      .filter(order -> order.getId().equals(id))
                      .findFirst();
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orders.size())
                 .filter(i -> Storage.orders.get(i).equals(order.getId()))
                 .findFirst()
                 .ifPresentOrElse(i -> Storage.orders.set(i, order), () -> {
                     throw new IllegalArgumentException("No order with id "
                        + order.getId() + " in storage");
                 });
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return Storage.orders.stream()
                      .filter(order -> order.getUserId().equals(userId))
                      .collect(Collectors.toList());
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.removeIf(order -> order.getId().equals(id));
    }
}